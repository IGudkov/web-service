package ru.courses.innotech.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import ru.courses.innotech.api.base.BasePostgresqlTest;
import ru.courses.innotech.api.model.entity.Product;
import ru.courses.innotech.api.model.repository.AgreementRepository;
import ru.courses.innotech.api.model.repository.ProductRepository;
import ru.courses.innotech.specs.exceptions.ServiceException;
import ru.courses.innotech.specs.model.account.create.AccountCreateRequest;
import ru.courses.innotech.specs.model.instance.create.InstanceCreateRequest;
import ru.courses.innotech.specs.model.instance.create.InstanceCreateResponse;

import java.io.FileNotFoundException;
import java.io.FileReader;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InstanceServiceTest extends BasePostgresqlTest {

  @Autowired
  private InstanceService instanceService;
  @Autowired
  private AgreementRepository agreementRepository;
  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private AccountService accountService;

  @Test
  @DisplayName("Успешное создание ЭП")
  public void createInstance() throws FileNotFoundException, ParseException, JsonProcessingException, ServiceException {
    InstanceCreateRequest instanceCreateRequest = getInstanceCreateRequestFromJsonFile("src/main/resources/json/instance-create.json");
    instanceCreateRequest.setContractNumber("2024-01-10-000001");
    Assertions.assertDoesNotThrow(() -> instanceService.create(instanceCreateRequest), "Ошибка создания ЭП");
  }

  @Test
  @DisplayName("Ошибка № договора уже существует для ЭП с ИД")
  public void createInstanceDouble() throws FileNotFoundException, ParseException, JsonProcessingException, ServiceException {
    InstanceCreateRequest instanceCreateRequest = getInstanceCreateRequestFromJsonFile("src/main/resources/json/instance-create.json");
    instanceCreateRequest.setContractNumber("2024-01-10-000002");
    ResponseEntity<InstanceCreateResponse> responseEntity = instanceService.create(instanceCreateRequest);
    Assertions.assertThrows(ServiceException.class,
        () -> instanceService.create(instanceCreateRequest),
        "Не допустимо создание нескольких ЭП с одним и тем же № договора");
  }

  @Test
  @DisplayName("Успешное создание ЭП + обновление ЭП + создание ДС")
  public void createInstanceAndAgreement() throws FileNotFoundException, ParseException, JsonProcessingException, ServiceException {
    InstanceCreateRequest instanceCreateRequest = getInstanceCreateRequestFromJsonFile("src/main/resources/json/instance-create.json");
    instanceCreateRequest.setContractNumber("2024-01-10-000003");
    ResponseEntity<InstanceCreateResponse> responseEntity = instanceService.create(instanceCreateRequest);
    Product product  = productRepository.findByNumber(instanceCreateRequest.getContractNumber()).orElse(null);
    InstanceCreateRequest instanceCreateRequestAgr = getInstanceCreateRequestFromJsonFile("src/main/resources/json/instance-agr.json");
    instanceCreateRequestAgr.setInstanceId(product.getId().toString());
    instanceCreateRequestAgr.setContractNumber(product.getNumber());
    ResponseEntity<InstanceCreateResponse> responseEntityAgr = instanceService.create(instanceCreateRequestAgr);
    Assertions.assertFalse(agreementRepository.findByProduct(product).isEmpty(), "ДС по ЭП не обнаружены");
  }

  @Test
  @DisplayName("Ошибка создания ПР если ЭП не найден")
  public void createAccountThrows() throws FileNotFoundException, ParseException, JsonProcessingException, ServiceException {
    AccountCreateRequest accountCreateRequest = getAccountCreateRequestFromJsonFile("src/main/resources/json/register-create.json");
    Assertions.assertThrows(ServiceException.class,
        () -> accountService.create(accountCreateRequest),
        "Создание ПР без ЭП не допустимо");
  }

  public static InstanceCreateRequest getInstanceCreateRequestFromJsonFile(String fileName) throws FileNotFoundException, ParseException, JsonProcessingException {
    Object obj = new JSONParser().parse(new FileReader(fileName));
    JSONObject jsonObj = (JSONObject) obj;
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(jsonObj.toString(), InstanceCreateRequest.class);
  }

  public static AccountCreateRequest getAccountCreateRequestFromJsonFile(String fileName) throws FileNotFoundException, ParseException, JsonProcessingException {
    Object obj = new JSONParser().parse(new FileReader(fileName));
    JSONObject jsonObj = (JSONObject) obj;
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(jsonObj.toString(), AccountCreateRequest.class);
  }

}
