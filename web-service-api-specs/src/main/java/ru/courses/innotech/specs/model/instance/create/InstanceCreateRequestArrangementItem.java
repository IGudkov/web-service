package ru.courses.innotech.specs.model.instance.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InstanceCreateRequestArrangementItem {

	@Schema(description = "ID доп. ген. соглашения", example = "123", nullable = true)
	private String generalAgreementId;
	@Schema(description = "ID доп. соглашения", example = "456", nullable = true)
	private String supplementaryAgreementId;
	@Schema(description = "Тип соглашения", example = "НСО", nullable = true)
	private String arrangementType;
	@Schema(description = "Идентификатор периодичности учета", example = "123456789", nullable = true)
	private Integer schedulerJobId;
	@Schema(description = "Номер ДС", example = "НСО-123")
	private String number;
	@Schema(description = "Дата начала сделки", example = "2024-01-11")
	private String openingDate;
	@Schema(description = "Дата окончания сделки", example = "2025-01-10", nullable = true)
	private String closingDate;
	@Schema(description = "Дата отзыва сделки", nullable = true, example = "2027-02-03")
	private String cancelDate;
	@Schema(description = "Срок действия сделки", example = "365", nullable = true)
	private Integer validityDuration;
	@Schema(description = "Причина расторжения", nullable = true)
	private String cancellationReason;
	@Schema(description = "Состояние/статус", example = "открыт", nullable = true)
	private String status;
	@Schema(description = "Начисление начинается с (дата)", nullable = true, example = "2024-01-12")
	private String interestCalculationDate;
	@Schema(description = "Процент начисления на остаток", example = "0", nullable = true)
	private BigDecimal interestRate;
	@Schema(description = "Коэффициент", example = "0", nullable = true)
	private BigDecimal coefficient;
	@Schema(description = "Действие коэффициента", nullable = true)
	private String coefficientAction;
	@Schema(description = "Минимум по ставке", example = "0", nullable = true)
	private BigDecimal minimumInterestRate;
	@Schema(description = "Коэффициент по минимальной ставке", example = "0", nullable = true)
	private BigDecimal minimumInterestRateCoefficient;
	@Schema(description = "Действие коэффициента по минимальной ставке", nullable = true)
	private String minimumInterestRateCoefficientAction;
	@Schema(description = "Максимум по ставке", example = "0", nullable = true)
	private BigDecimal maximalInterestRate;
	@Schema(description = "Коэффициент по максимальной ставке", example = "0", nullable = true)
	private BigDecimal maximalInterestRateCoefficient;
	@Schema(description = "Действие коэффициента по максимальной ставке", nullable = true)
	private String maximalInterestRateCoefficientAction;

}
