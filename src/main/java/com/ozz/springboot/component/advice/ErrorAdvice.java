package com.ozz.springboot.component.advice;

import com.alibaba.fastjson.JSON;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(basePackages = "com.ozz.springboot.web")
public class ErrorAdvice {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @ExceptionHandler({Exception.class})
  @ResponseBody
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> httpStatusHandler(HttpServletResponse response, Exception e) {
//    throw HttpClientErrorException.create(HttpStatus.UNAUTHORIZED, "认证失败", null, null, null);
    return Collections.singletonMap("message", StringUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getName());
  }
}
