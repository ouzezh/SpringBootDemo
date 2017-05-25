package com.ozz.springboot.advice;

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
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(value = {SQLException.class})
  protected ResponseEntity<Object> handleConflict(SQLException ex, WebRequest request) {
    log.error(null, ex);
    String bodyOfResponse = "系统错误，请联系管理员！";
    return handleExceptionInternal(ex,
                                   bodyOfResponse,
                                   new HttpHeaders(),
                                   HttpStatus.INTERNAL_SERVER_ERROR,
                                   request);
  }
}
