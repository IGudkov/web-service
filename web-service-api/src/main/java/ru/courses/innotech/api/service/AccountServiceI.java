package ru.courses.innotech.api.service;

import org.springframework.http.ResponseEntity;
import ru.courses.innotech.specs.exceptions.ServiceException;
import ru.courses.innotech.specs.model.account.create.AccountCreateRequest;
import ru.courses.innotech.specs.model.account.create.AccountCreateResponse;

public interface AccountServiceI {

  ResponseEntity<AccountCreateResponse> create(AccountCreateRequest accountCreateRequest) throws ServiceException;

}
