package ru.courses.innotech.api.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tpp_agreement")
public class Agreement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "general_agreement_id")
  private String generalAgreementId;

  @Column(name = "supplementary_agreement_id")
  private String supplementaryAgreementId;

  @Column(name = "arrangement_type")
  private String arrangementType;

  @Column(name = "sheduler_job_id")
  private Integer shedulerJobId;

  @Column(name = "number")
  private String number;

  @Column(name = "opening_date")
  private LocalDate openingDate;

  @Column(name = "closing_date")
  private LocalDate closingDate;

  @Column(name = "cancel_date")
  private LocalDate cancelDate;

  @Column(name = "validity_duration")
  private Integer validityDuration;

  @Column(name = "cancellation_reason")
  private String cancellationReason;

  @Column(name = "status")
  private String status;

  @Column(name = "interest_calculation_date")
  private LocalDate interestCalculationDate;

  @Column(name = "interest_rate")
  private BigDecimal interestRate;

  @Column(name = "coefficient")
  private BigDecimal coefficient;

  @Column(name = "coefficient_action")
  private String coefficientAction;

  @Column(name = "minimum_interest_rate")
  private BigDecimal minimumInterestRate;

  @Column(name = "minimum_interest_rate_coefficient")
  private BigDecimal minimumInterestRateCoefficient;

  @Column(name = "minimum_interest_rate_coefficient_action")
  private String minimumInterestRateCoefficientAction;

  @Column(name = "maxima_interest_rate")
  private BigDecimal maximaInterestRate;

  @Column(name = "maxima_interest_rate_coefficient")
  private BigDecimal maximaInterestRateCoefficient;

  @Column(name = "maxima_interest_rate_coefficient_action")
  private String maximaInterestRateCoefficientAction;

}
