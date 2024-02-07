package ru.courses.innotech.specs.model.instance.create;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class InstanceCreateRequestPropertyItem {

	@Schema(description = "Ключ")
	private String key;
	@Schema(description = "Значение")
	private String value;

}
