package ru.courses.innotech.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.courses.innotech.api.model.entity.AccountPool;
import ru.courses.innotech.api.model.entity.Product;
import ru.courses.innotech.api.model.entity.ProductRegister;
import ru.courses.innotech.api.model.entity.dict.TppRefProductRegisterType;
import ru.courses.innotech.api.model.repository.AccountPoolRepository;
import ru.courses.innotech.api.model.repository.ProductRegisterRepository;
import ru.courses.innotech.api.model.repository.ProductRepository;
import ru.courses.innotech.api.model.repository.dict.TppRefProductRegisterTypeRepository;
import ru.courses.innotech.specs.exceptions.ServiceException;
import ru.courses.innotech.specs.model.account.create.AccountCreateRequest;
import ru.courses.innotech.specs.model.account.create.AccountCreateResponse;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final ProductRepository productRepository;
  private final ProductRegisterRepository productRegisterRepository;
  private final TppRefProductRegisterTypeRepository tppRefProductRegisterTypeRepository;
  private final AccountPoolRepository accountPoolRepository;

  @Transactional
  public ResponseEntity<AccountCreateResponse> create(AccountCreateRequest accountCreateRequest) throws ServiceException {

    Product product;

    //ШАГ 1. Валидация параметров
    String validationErrorMessage = validation(accountCreateRequest);
    if (!(validationErrorMessage == null || validationErrorMessage.isEmpty())) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, validationErrorMessage);
    }

    product = productRepository.findById(Long.valueOf(accountCreateRequest.getInstanceId()))
        .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND,
                MessageFormat.format("ЭП с Id {0} не найден", accountCreateRequest.getInstanceId())
            )
        );

    TppRefProductRegisterType tppRefProductRegisterType = tppRefProductRegisterTypeRepository
        .findByProductClassCodeAndValue(product.getProductCode(), accountCreateRequest.getRegistryTypeCode())
        .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND,
                MessageFormat.format("Тип регистра с кодом класса продукта {0} и значением {1} не найден", product.getProductCode(), accountCreateRequest.getRegistryTypeCode())
            )
        );

    //Шаг 2.Проверка таблицы ПР на дубли
    List<ProductRegister> productRegisterList = productRegisterRepository.findByProductAndTppRefProductRegisterType(product, tppRefProductRegisterType);
    if (!productRegisterList.isEmpty()) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, MessageFormat.format("Параметр registryTypeCode тип регистра {0} уже существует для ЭП с ИД  {1}", accountCreateRequest.getRegistryTypeCode(), accountCreateRequest.getInstanceId()));
    }

    //Шаг 4.
    AccountPool accountPool = accountPoolRepository.findFirstByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(
        accountCreateRequest.getBranchCode(),
        accountCreateRequest.getCurrencyCode(),
        accountCreateRequest.getMdmCode(),
        accountCreateRequest.getPriorityCode(),
        accountCreateRequest.getRegistryTypeCode()
    ).orElse(null);

    ProductRegister productRegister = new ProductRegister();
    productRegister.setProduct(product);
    if (accountPool != null) {
      productRegister.setAccountNumber(accountPool.getAccount());
    }
    productRegister.setCurrencyCode(accountCreateRequest.getCurrencyCode());
    productRegister.setTppRefProductRegisterType(tppRefProductRegisterType);
    productRegister.setState("Открыт");

    productRegisterRepository.save(productRegister);

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(AccountCreateResponse.builder()
            .instanceId(productRegister.getId())
            .build());
  }

  String validation(AccountCreateRequest accountCreateRequest) {
    if (accountCreateRequest.getInstanceId() == null || accountCreateRequest.getInstanceId().isEmpty()) {
      return "instanceId не заполнено";
    }
    return null;
  }

}
