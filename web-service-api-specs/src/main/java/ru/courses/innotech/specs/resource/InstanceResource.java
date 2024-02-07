package ru.courses.innotech.specs.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.courses.innotech.specs.exceptions.ServiceException;
import ru.courses.innotech.specs.model.ErrorResponse;
import ru.courses.innotech.specs.model.instance.create.InstanceCreateRequest;
import ru.courses.innotech.specs.model.instance.create.InstanceCreateResponse;

import javax.validation.Valid;

@Validated
@RequestMapping("/corporate-settlement-instance")
@Tag(name = "ЭКЗЕМПЛЯР ПРОДУКТА (ЭП)", description = "Взаимодействие с продуктом")
public interface InstanceResource {

  @PostMapping(value = "/create",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  @Operation(summary = "Создание нового объекта ЭКЗЕМПЛЯРА ПРОДУКТА (ЭП)",
      description = "Создание нового объекта ЭКЗЕМПЛЯРА ПРОДУКТА (ЭП)")
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "OK",
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = InstanceCreateResponse.class)
              )
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Bad Request",
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorResponse.class)
              )
          }
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Unauthorized",
          content = @Content(schema = @Schema(hidden = true))
  ),
      @ApiResponse(
          responseCode = "403",
          description = "Forbidden",
          content = @Content(schema = @Schema(hidden = true))
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Not found",
          content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = ErrorResponse.class)
              )
          }

      ),
      @ApiResponse(
          responseCode = "500",
          description = "Server Error",
          content = @Content(schema = @Schema(hidden = true))
      ),
  })
  ResponseEntity<InstanceCreateResponse> create(
      @RequestBody @Valid InstanceCreateRequest instanceCreateRequest) throws ServiceException;

}
