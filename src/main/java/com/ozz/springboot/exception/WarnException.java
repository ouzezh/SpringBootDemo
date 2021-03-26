package com.ozz.springboot.exception;

public class WarnException extends RuntimeException {
  public WarnException(String message) {
    super(message);
  }
  public WarnException(Throwable cause) {
    super(cause);
  }
  public WarnException(String message, Throwable cause) {
    super(message, cause);
  }
}
