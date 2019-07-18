package com.ozz.springboot.component.advice;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ozz.springboot.util.JsonUtil;

/**
 * 针对SQL异常定制化处理，防止数据库信息泄露到前端
 */
@ControllerAdvice(basePackages = "com.ozz.springboot.web")
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
    return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error(null, ex);
    headers.add("Content-Type", "application/json;charset=UTF-8");
    Map<String, Object> map = new HashMap<>();
    map.put("message", ex.getMessage()!=null?ex.getMessage():ex.getClass().getName());
    body = JsonUtil.toJson(map);
    return super.handleExceptionInternal(ex, body, headers, status, request);
  }
}
