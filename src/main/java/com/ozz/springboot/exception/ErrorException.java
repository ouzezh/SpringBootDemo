package com.ozz.springboot.exception;

public class ErrorException extends RuntimeException {
  public ErrorException(String message) {
    super(message);
  }
  public ErrorException(Throwable cause) {
    super(cause);
  }
  public ErrorException(String message, Throwable cause) {
    super(message, cause);
  }
}
