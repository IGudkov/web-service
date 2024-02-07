package ru.courses.innotech.specs.model.instance.create;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstanceCreateResponse {

	@Schema(description = "Идентификатор экземпляра продукта", example = "12345")
	private Long instanceId;
	@Schema(description = "Массив идентификаторов продуктового регистра")
	private List<String> registerId;
	@Schema(description = "Массив ID доп. соглашения")
	private List<String> supplementaryAgreementId;

}
