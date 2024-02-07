package ru.courses.innotech.specs.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ServiceException extends Exception {

  private final HttpStatus httpStatus;

  public ServiceException(HttpStatus httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
  }

}
