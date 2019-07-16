package com.ozz.springboot.component.advice;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 针对SQL异常定制化处理，防止数据库信息泄露到前端
 */
@ControllerAdvice(basePackages = "com.ozz.springboot.web")
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @ExceptionHandler({SQLException.class})
  protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json;charset=UTF-8");
    return handleExceptionInternal(ex,
                                   String.format("{\"status\": 500,\"message\": \"%s\"}", ex.getMessage()!=null?ex.getMessage():ex.getClass().getName()),
                                   headers,
                                   HttpStatus.INTERNAL_SERVER_ERROR,
                                   request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error(null, ex);
    return super.handleExceptionInternal(ex, body, headers, status, request);
  }
}
