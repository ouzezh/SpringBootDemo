package com.ozz.springboot.config.advice;

import com.ozz.springboot.exception.WarnException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ErrorAdvice {

  @ExceptionHandler({Exception.class})
  @ResponseBody
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, Object> exceptionHandler(HttpServletResponse response, Exception e) {
    if (e instanceof WarnException) {
      log.warn(e.getMessage());
    } else {
      log.error(null, e);
    }
    return Collections
        .singletonMap("message", Objects.toString(e.getMessage(), e.getClass().getName()));
  }
}
