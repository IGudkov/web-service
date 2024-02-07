package ru.courses.innotech.specs.model.account.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AccountCreateRequest {

	@Schema(description = "Идентификатор экземпляра продукта", example = "1")
	private String instanceId;
	@Schema(description = "Тип регистра", example = "03.012.002_47533_ComSoLd", nullable = true)
	private String registryTypeCode;
	@Schema(description = "Тип счета", example = "Клиентский", nullable = true)
	private String accountType;
	@Schema(description = "Код валюты", example = "800", nullable = true)
	private String currencyCode;
	@Schema(description = "Код филиала", example = "0022", nullable = true)
	private String branchCode;
	@Schema(description = "Код срочности", example = "00", nullable = true)
	private String priorityCode;
	@Schema(description = "Id клиента", example = "15", nullable = true)
	private String mdmCode;
	@Schema(description = "Код клиента", example = "15-12345", nullable = true)
	private String clientCode;
	@Schema(description = "Регион принадлежности железной дороги", example = "ABC", nullable = true)
	private String trainRegion;
	@Schema(description = "Счетчик", example = "123", nullable = true)
	private String counter;
	@Schema(description = "Код точки продаж", nullable = true)
	private String salesCode;

}
