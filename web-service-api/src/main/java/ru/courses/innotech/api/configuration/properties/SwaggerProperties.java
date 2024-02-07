package ru.courses.innotech.api.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@Data
@ConfigurationProperties("app.swagger")
public class SwaggerProperties {

  @NotBlank
  private String title;
  @NotBlank
  private String version;
  @NotBlank
  private String description;
  @NotBlank
  private String license;
  @NotNull
  private SwaggerContactData contact;
  @NotEmpty
  private List<SwaggerServerData> servers;

  @Data
  public static class SwaggerContactData {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String url;

  }

  @Data
  public static class SwaggerServerData {

    @NotBlank
    private String url;
    @NotBlank
    private String description;

  }

}
