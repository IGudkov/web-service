package ru.courses.innotech.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.courses.innotech.specs.exceptions.ServiceException;
import ru.courses.innotech.api.model.entity.AccountPool;
import ru.courses.innotech.api.model.entity.Agreement;
import ru.courses.innotech.api.model.entity.Product;
import ru.courses.innotech.api.model.entity.ProductRegister;
import ru.courses.innotech.api.model.entity.dict.TppRefProductRegisterType;
import ru.courses.innotech.api.model.repository.AccountPoolRepository;
import ru.courses.innotech.api.model.repository.AgreementRepository;
import ru.courses.innotech.api.model.repository.ProductRegisterRepository;
import ru.courses.innotech.api.model.repository.ProductRepository;
import ru.courses.innotech.api.model.repository.dict.TppRefProductRegisterTypeRepository;
import ru.courses.innotech.api.utils.CustomDateUtil;
import ru.courses.innotech.specs.model.instance.create.InstanceCreateRequest;
import ru.courses.innotech.specs.model.instance.create.InstanceCreateRequestArrangementItem;
import ru.courses.innotech.specs.model.instance.create.InstanceCreateResponse;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static ru.courses.innotech.api.utils.CustomDateUtil.DATE_TIME_FORMATTER_YYYY_MM_DD;

@Service
@RequiredArgsConstructor
public class InstanceService {

  private final ProductRepository productRepository;
  private final TppRefProductRegisterTypeRepository tppRefProductRegisterTypeRepository;
  private final ProductRegisterRepository productRegisterRepository;
  private final AgreementRepository agreementRepository;
  private final AccountPoolRepository accountPoolRepository;

  @Transactional
  public ResponseEntity<InstanceCreateResponse> create(InstanceCreateRequest instanceCreateRequest) throws ServiceException {

    Product product;

    //ШАГ 1. Валидация обязательных параметров
    String validationErrorMessage = validation(instanceCreateRequest);
    if (!(validationErrorMessage == null || validationErrorMessage.isEmpty())) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, validationErrorMessage);
    }

    if (instanceCreateRequest.getInstanceId() == null || instanceCreateRequest.getInstanceId().isEmpty()) {
      //Шаг 2. Проверка таблицы ЭП на дубли
      product = productRepository.findByNumber(instanceCreateRequest.getContractNumber()).orElse(null);
      if (product != null) {
        throw new ServiceException(HttpStatus.BAD_REQUEST, MessageFormat.format("№ договора {0} уже существует для ЭП с ИД {1}", instanceCreateRequest.getContractNumber(), product.getId()));
      }

      //Шаг 3.Проверка таблицы ДС на дубли
      if (instanceCreateRequest.getArrangements() != null) {
        for (InstanceCreateRequestArrangementItem arrangement : instanceCreateRequest.getArrangements()) {
          if (agreementRepository.existsByNumber(arrangement.getNumber())) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, MessageFormat.format("Параметр № Дополнительного соглашения (сделки) Number {0} уже существует", arrangement.getNumber()));
          }
        }
      }

      //Шаг 5.
      product = new Product();
      product.setNumber(instanceCreateRequest.getContractNumber());
      product.setDateOfConclusion(CustomDateUtil.getLocalDate(instanceCreateRequest.getContractDate(), DATE_TIME_FORMATTER_YYYY_MM_DD));
      product.setPriority(instanceCreateRequest.getPriority());
      product.setThresholdAmount(instanceCreateRequest.getThresholdAmount());
      product.setProductCode(instanceCreateRequest.getProductCode());
      product.setState("Открыт");
      productRepository.save(product);

    } else {
      //Шаг 8.Если ИД ЭП != NULL, то изменяется состав ДС
      product = productRepository.findById(Long.valueOf(instanceCreateRequest.getInstanceId()))
          .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND,
                  MessageFormat.format("ЭП с Id {0} не найден", instanceCreateRequest.getInstanceId())
              )
          );

      if (instanceCreateRequest.getArrangements() != null) {
        List<Agreement> agreementList = new ArrayList<>();
        for (InstanceCreateRequestArrangementItem arrangement : instanceCreateRequest.getArrangements()) {
          Agreement agreement = agreementRepository.findByProductAndNumber(product, arrangement.getNumber()).orElse(new Agreement());
          agreement.setProduct(product);
          agreement.setGeneralAgreementId(arrangement.getGeneralAgreementId());
          agreement.setSupplementaryAgreementId(arrangement.getSupplementaryAgreementId());
          agreement.setArrangementType(arrangement.getArrangementType());
          agreement.setShedulerJobId(arrangement.getSchedulerJobId());
          agreement.setNumber(arrangement.getNumber());
          agreement.setOpeningDate(CustomDateUtil.getLocalDate(arrangement.getOpeningDate(), DATE_TIME_FORMATTER_YYYY_MM_DD));
          agreement.setClosingDate(CustomDateUtil.getLocalDate(arrangement.getClosingDate(), DATE_TIME_FORMATTER_YYYY_MM_DD));
          agreement.setCancelDate(CustomDateUtil.getLocalDate(arrangement.getCancelDate(), DATE_TIME_FORMATTER_YYYY_MM_DD));
          agreement.setValidityDuration(arrangement.getValidityDuration());
          agreement.setCancellationReason(arrangement.getCancellationReason());
          agreement.setStatus(arrangement.getStatus());
          agreement.setInterestCalculationDate(CustomDateUtil.getLocalDate(arrangement.getInterestCalculationDate(), DATE_TIME_FORMATTER_YYYY_MM_DD));
          agreement.setInterestRate(arrangement.getInterestRate());
          agreement.setCoefficient(arrangement.getCoefficient());
          agreement.setCoefficientAction(arrangement.getCoefficientAction());
          agreement.setMinimumInterestRate(arrangement.getMinimumInterestRate());
          agreement.setMinimumInterestRateCoefficient(arrangement.getMinimumInterestRateCoefficient());
          agreement.setMinimumInterestRateCoefficientAction(arrangement.getMinimumInterestRateCoefficientAction());
          agreement.setMaximaInterestRate(arrangement.getMaximalInterestRate());
          agreement.setMaximaInterestRateCoefficient(arrangement.getMaximalInterestRateCoefficient());
          agreement.setMaximaInterestRateCoefficientAction(arrangement.getMaximalInterestRateCoefficientAction());
          agreementList.add(agreement);
        }
        agreementRepository.saveAll(agreementList);
      }
    }

    TppRefProductRegisterType tppRefProductRegisterType = tppRefProductRegisterTypeRepository
        .findByProductClassCodeAndAccountTypeCode(instanceCreateRequest.getProductCode(), "Клиентский")
        .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND,
                MessageFormat.format("Клиентский тип регистра с кодом класса продукта {0}} не найден", instanceCreateRequest.getProductCode())
            )
        );

    AccountPool accountPool = accountPoolRepository.findFirstByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(
        instanceCreateRequest.getBranchCode(),
        instanceCreateRequest.getIsoCurrencyCode(),
        instanceCreateRequest.getMdmCode(),
        instanceCreateRequest.getUrgencyCode(),
        instanceCreateRequest.getRegisterType()
    ).orElse(null);

    if (accountPool != null) {
      ProductRegister productRegister = new ProductRegister();
      productRegister.setProduct(product);
      productRegister.setAccountNumber(accountPool.getAccount());
      productRegister.setTppRefProductRegisterType(tppRefProductRegisterType);
      productRegister.setCurrencyCode(instanceCreateRequest.getIsoCurrencyCode());
      productRegister.setState("Открыт");
      productRegisterRepository.save(productRegister);
    }

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(InstanceCreateResponse.builder()
            .instanceId(product.getId())
            .registerId(productRegisterRepository.findByProduct(product).stream().map(x -> x.getId().toString()).toList())
            .supplementaryAgreementId(agreementRepository.findByProduct(product).stream().map(x -> x.getId().toString()).toList())
            .build()
        );
  }

  String validation(InstanceCreateRequest instanceCreateRequest) {
    List<String> errorList = new ArrayList<>();
    if (instanceCreateRequest.getProductType() == null || instanceCreateRequest.getProductType().isEmpty()) {
      errorList.add("productType не заполнено");
    }
    if (instanceCreateRequest.getProductCode() == null || instanceCreateRequest.getProductCode().isEmpty()) {
      errorList.add("productCode не заполнено");
    }
    if (instanceCreateRequest.getRegisterType() == null || instanceCreateRequest.getRegisterType().isEmpty()) {
      errorList.add("registerType не заполнено");
    }
    if (instanceCreateRequest.getMdmCode() == null || instanceCreateRequest.getMdmCode().isEmpty()) {
      errorList.add("mdmCode не заполнено");
    }
    if (instanceCreateRequest.getContractNumber() == null || instanceCreateRequest.getContractNumber().isEmpty()) {
      errorList.add("contractNumber не заполнено");
    }

    if (instanceCreateRequest.getContractDate() == null || instanceCreateRequest.getContractDate().isEmpty()) {
      errorList.add("contractDate не заполнено");
    }
    if (instanceCreateRequest.getPriority() == null) {
      errorList.add("priority не заполнено");
    }
    if (instanceCreateRequest.getContractId() == null) {
      errorList.add("contractId не заполнено");
    }
    if (instanceCreateRequest.getBranchCode() == null || instanceCreateRequest.getBranchCode().isEmpty()) {
      errorList.add("branchCode не заполнено");
    }
    if (instanceCreateRequest.getIsoCurrencyCode() == null || instanceCreateRequest.getIsoCurrencyCode().isEmpty()) {
      errorList.add("isoCurrencyCode не заполнено");
    }
    if (instanceCreateRequest.getUrgencyCode() == null || instanceCreateRequest.getUrgencyCode().isEmpty()) {
      errorList.add("urgencyCode не заполнено");
    }

    return String.join("\n", errorList);
  }

}
