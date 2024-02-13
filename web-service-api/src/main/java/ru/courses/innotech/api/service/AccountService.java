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
import ru.courses.innotech.specs.exceptions.ServiceException;
import ru.courses.innotech.specs.model.account.create.AccountCreateRequest;
import ru.courses.innotech.specs.model.account.create.AccountCreateResponse;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountServiceI {

  private final AccountPoolSrv accountPoolSrv;
  private final ProductSrv productSrv;
  private final ProductRegisterSrv productRegisterSrv;
  private final TppRefProductRegisterTypeSrv tppRefProductRegisterTypeSrv;

  @Transactional
  public ResponseEntity<AccountCreateResponse> create(AccountCreateRequest accountCreateRequest) throws ServiceException {

    Product product;

    //ШАГ 1. Валидация параметров
    String validationErrorMessage = validation(accountCreateRequest);
    if (!(validationErrorMessage == null || validationErrorMessage.isEmpty())) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, validationErrorMessage);
    }

    product = productSrv.getProductById(Long.valueOf(accountCreateRequest.getInstanceId()));
    if (product == null) {
      throw new ServiceException(HttpStatus.NOT_FOUND,
          MessageFormat.format("ЭП с Id {0} не найден", accountCreateRequest.getInstanceId())
      );
    }

    TppRefProductRegisterType tppRefProductRegisterType = tppRefProductRegisterTypeSrv.getTppRefProductRegisterType(
        product.getProductCode(),
        accountCreateRequest.getRegistryTypeCode());
    if (tppRefProductRegisterType == null) {
      throw new ServiceException(HttpStatus.NOT_FOUND,
          MessageFormat.format("Тип регистра с кодом класса продукта {0} и значением {1} не найден", product.getProductCode(), accountCreateRequest.getRegistryTypeCode())
      );
    }

    //Шаг 2.Проверка таблицы ПР на дубли
    List<ProductRegister> productRegisterList = productRegisterSrv.getListProductRegister(product, tppRefProductRegisterType);
    if (!productRegisterList.isEmpty()) {
      throw new ServiceException(HttpStatus.BAD_REQUEST, MessageFormat.format("Параметр registryTypeCode тип регистра {0} уже существует для ЭП с ИД  {1}", accountCreateRequest.getRegistryTypeCode(), accountCreateRequest.getInstanceId()));
    }

    //Шаг 4.
    AccountPool accountPool = accountPoolSrv.getAccountPool(
        accountCreateRequest.getBranchCode(),
        accountCreateRequest.getCurrencyCode(),
        accountCreateRequest.getMdmCode(),
        accountCreateRequest.getPriorityCode(),
        accountCreateRequest.getRegistryTypeCode());

    ProductRegister productRegister = new ProductRegister();
    productRegister.setProduct(product);
    if (accountPool != null) {
      productRegister.setAccountNumber(accountPool.getAccount());
    }
    productRegister.setCurrencyCode(accountCreateRequest.getCurrencyCode());
    productRegister.setTppRefProductRegisterType(tppRefProductRegisterType);
    productRegister.setState("Открыт");

    productRegisterSrv.save(productRegister);

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(AccountCreateResponse.builder()
            .instanceId(productRegister.getId())
            .build());
  }

  private String validation(AccountCreateRequest accountCreateRequest) {
    if (accountCreateRequest.getInstanceId() == null || accountCreateRequest.getInstanceId().isEmpty()) {
      return "instanceId не заполнено";
    }
    return null;
  }

}
