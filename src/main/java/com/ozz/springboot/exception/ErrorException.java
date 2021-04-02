package com.ozz.springboot.exception;

import java.util.Objects;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorException extends RuntimeException {
  HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

  public ErrorException(String message) {
    super(message);
  }
  public ErrorException(Throwable cause) {
    super(cause);
  }
  public ErrorException(String message, Throwable cause) {
    super(message, cause);
  }
  public ErrorException(HttpStatus status, String message) {
    super(String.format("%s %s", status.value(), message));
    this.status = status;
  }
  @Override
  public String getMessage() {
    return Objects.toString(super.getMessage(), getClass().getSimpleName());
  }
}
