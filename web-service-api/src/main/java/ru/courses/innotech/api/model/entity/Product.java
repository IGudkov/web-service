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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tpp_product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "agreement_id")
  private Integer agreementId;

  @Column(name = "product_code")
  private String productCode;

  @Column(name = "client_id")
  private Integer clientId;

  @Column(name = "type")
  private String type;

  @Column(name = "number")
  private String number;

  @Column(name = "priority")
  private Integer priority;

  @Column(name = "date_of_conclusion")
  private LocalDate dateOfConclusion;

  @Column(name = "start_date_time")
  private LocalDateTime startDateTime;

  @Column(name = "end_date_time")
  private LocalDateTime endDateTime;

  @Column(name = "days")
  private Integer days;

  @Column(name = "nso")
  private BigDecimal nso;

  @Column(name = "threshold_amount")
  private BigDecimal thresholdAmount;

  @Column(name = "requlsite_type")
  private String requlsiteType;

  @Column(name = "interest_rate_type")
  private String interestRateType;

  @Column(name = "tax_rate")
  private BigDecimal taxRate;

  @Column(name = "reason_close")
  private String reasonClose;

  @Column(name = "state")
  private String state;

}
