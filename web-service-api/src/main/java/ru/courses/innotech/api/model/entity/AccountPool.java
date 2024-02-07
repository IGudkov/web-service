package ru.courses.innotech.api.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tpp_account_pool")
public class AccountPool {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "branch_code")
  private String branchCode;

  @Column(name = "currency_code")
  private String currencyCode;

  @Column(name = "mdm_code")
  private String mdmCode;

  @Column(name = "priority_code")
  private String priorityCode;

  @Column(name = "registry_type_code")
  private String registryTypeCode;

  @Column(name = "account")
  private String account;

}
