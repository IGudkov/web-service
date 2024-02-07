package ru.courses.innotech.specs.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

	@Schema(description = "Текст ошибки")
	private String message;
	@Schema(description = "Стек-трейс ошибки", nullable = true)
	private String stackTrace;

}
