package com.ozz.springboot.exception;

import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class WarnException extends RuntimeException {
  HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
  public WarnException(String message) {
    super(message);
  }
  public WarnException(HttpStatus status, String message) {
    super(String.format("%s %s", status.value(), message));
    this.status = status;
  }
  @Override
  public String getMessage() {
    return Objects.toString(super.getMessage(), getClass().getSimpleName());
  }
}
