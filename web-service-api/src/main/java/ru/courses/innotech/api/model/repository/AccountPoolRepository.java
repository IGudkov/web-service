package ru.courses.innotech.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.courses.innotech.api.model.entity.AccountPool;

import java.util.Optional;


public interface AccountPoolRepository extends JpaRepository<AccountPool, Long> {

Optional<AccountPool> findFirstByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(String branchCode, String currencyCode, String mdmCode, String priorityCode, String registryTypeCode);

}
