package com.ozz.springboot.config.interceptor;

import com.ozz.springboot.exception.ErrorException;
import com.ozz.springboot.exception.WarnException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class ErrorAdvice {

  /**
   * 处理异常
   * <p>
   * 强制指定返回状态码: @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
   */
  @ExceptionHandler({WarnException.class})
  @ResponseBody
  public Map<String, Object> warnHandler(HttpServletResponse response, WarnException e) {
    response.setStatus(e.getStatus().value());
    return getMessage(e);
  }

  @ExceptionHandler({ErrorException.class})
  @ResponseBody
  public Map<String, Object> errorHandler(HttpServletResponse response, ErrorException e) {
    log.error(null, e);
    response.setStatus(e.getStatus().value());
    return getMessage(e);
  }

  private Map<String, Object> getMessage(Throwable e) {
    return Collections
            .singletonMap("message", Objects.toString(e.getMessage(), e.getClass().getName()));
  }
}
