package com.ozz.springboot.config.advice;

import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(basePackages = "com.ozz.springboot.web")
public class ErrorAdvice {

  @ExceptionHandler({Exception.class})
  @ResponseBody
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> httpStatusHandler(HttpServletResponse response, Exception e) {
    log.error(null, e);
    return Collections.singletonMap("message", StringUtils.isNotEmpty(e.getMessage()) ? e.getMessage() : e.getClass().getName());
  }
}
