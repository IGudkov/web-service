package ru.courses.innotech.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.courses.innotech.specs.exceptions.ServiceException;
import ru.courses.innotech.specs.model.ErrorResponse;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<ErrorResponse> serviceException(ServiceException e) {
    return ResponseEntity
        .status(e.getHttpStatus())
        .body(ErrorResponse.builder()
            .message(e.getMessage())
            .stackTrace(Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining(System.lineSeparator())))
            .build()
        );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> exception(Exception e) {
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.builder()
            .message(e.getMessage())
            .stackTrace(Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining(System.lineSeparator())))
            .build()
        );
  }

}
