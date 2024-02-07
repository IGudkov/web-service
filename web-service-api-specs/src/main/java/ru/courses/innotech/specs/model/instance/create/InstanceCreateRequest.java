package ru.courses.innotech.specs.model.instance.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class InstanceCreateRequest {

	@Schema(description = "Идентификатор экземпляра продукта", example = "1", nullable = true)
	private String instanceId;
	@Schema(description = "Тип Экземпляра Продукта", example = "ДОГОВОР")
	private String productType;
	@Schema(description = "Код продукта в каталоге продуктов", example = "03.012.002")
	private String productCode;
	@Schema(description = "Тип регистра", example = "03.012.002_47533_ComSoLd")
	private String registerType;
	@Schema(description = "Код Клиента (mdm)", example = "15")
	private String mdmCode;
	@Schema(description = "Номер договора", example = "2024-01-10-000001")
	private String contractNumber;
	@Schema(description = "Дата заключения договора обслуживания", example = "2024-01-10")
	private String contractDate;
	@Schema(description = "Приоритет", example = "2")
	private Integer priority;
	@Schema(description = "Штрафная процентная ставка", example = "12.25", nullable = true)
	private BigDecimal interestRatePenalty;
	@Schema(description = "Неснижаемый остаток", example = "0", nullable = true)
	private BigDecimal minimalBalance;
	@Schema(description = "Пороговая сумма", example = "0", nullable = true)
	private BigDecimal thresholdAmount;
	@Schema(description = "Реквизиты выплаты", example = "qwerty", nullable = true)
	private String accountingDetails;
	@Schema(description = "Выбор ставки в зависимости от суммы", example = "0", nullable = true)
	private String rateType;
	@Schema(description = "Ставка налогообложения", example = "13", nullable = true)
	private BigDecimal taxPercentageRate;
	@Schema(description = "Сумма лимита технического овердрафта", example = "1000", nullable = true)
	private BigDecimal technicalOverdraftLimitAmount;
	@Schema(description = "ID Договора", example = "0")
	private Integer contractId;
	@Schema(description = "Код филиала", example = "0022")
	private String branchCode;
	@Schema(description = "Код валюты", example = "800")
	private String isoCurrencyCode;
	@Schema(description = "Код срочности договора", example = "00")
	private String urgencyCode;
	@Schema(description = "Код точки продаж", example = "1234", nullable = true)
	private String referenceCode;
	@Schema(description = "Масссив дополнительных признаков", nullable = true)
	private List<InstanceCreateRequestPropertyItem> properties;
	@Schema(description = "Массив доп.соглашений", nullable = true)
	private List<InstanceCreateRequestArrangementItem> arrangements;

}
