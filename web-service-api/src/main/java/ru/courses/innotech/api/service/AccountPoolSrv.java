package ru.courses.innotech.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.courses.innotech.api.model.entity.AccountPool;
import ru.courses.innotech.api.model.repository.AccountPoolRepository;

@Service
@RequiredArgsConstructor
public class AccountPoolSrv {

  private final AccountPoolRepository accountPoolRepository;

  AccountPool getAccountPool(
      String branchCode,
      String currencyCode,
      String mdmCode,
      String priorityCode,
      String registryTypeCode) {
    return accountPoolRepository.findFirstByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(
        branchCode,
        currencyCode,
        mdmCode,
        priorityCode,
        registryTypeCode
    ).orElse(null);
  }

}
