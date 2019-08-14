package com.ozz.springboot.component.advice;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

@ControllerAdvice(basePackages = "com.ozz.springboot.web")
public class SampleResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
    return handleExceptionInternal(ex, null, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  /**
   * 异常处理（必须覆盖，否则标准异常处理的提示会没有）
   *
   */
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error(body == null ? null : body.toString(), ex);

    if (body == null && ex != null) {
      Map<String, Object> map = Collections.singletonMap("message", StringUtils.isNotEmpty(ex.getMessage()) ? ex.getMessage() : ex.getClass().getName());
      body = JsonUtil.toJson(map);
      if (headers == null) {
        headers = new HttpHeaders();
      }
      headers.add("Content-Type", "application/json");
    }

    return super.handleExceptionInternal(ex, body, headers, status, request);
  }
}
