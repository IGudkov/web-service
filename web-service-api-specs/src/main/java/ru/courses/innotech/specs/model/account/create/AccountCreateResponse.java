package ru.courses.innotech.specs.model.account.create;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountCreateResponse {

	@Schema(description = "Идентификатор продуктового регистра", example = "1")
	private Long instanceId;

}
