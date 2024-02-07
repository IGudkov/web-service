package ru.courses.innotech.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.courses.innotech.api.service.AccountService;
import ru.courses.innotech.specs.exceptions.ServiceException;
import ru.courses.innotech.specs.model.account.create.AccountCreateRequest;
import ru.courses.innotech.specs.model.account.create.AccountCreateResponse;
import ru.courses.innotech.specs.model.instance.create.InstanceCreateRequest;
import ru.courses.innotech.specs.resource.AccountResource;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController implements AccountResource {

  private final AccountService accountService;

  @Override
  public ResponseEntity<AccountCreateResponse> create(AccountCreateRequest accountCreateRequest) throws ServiceException {

    return accountService.create(accountCreateRequest);
  }
}
