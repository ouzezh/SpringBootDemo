package com.ozz.springboot.config.advice;

import com.ozz.springboot.exception.ErrorException;
import com.ozz.springboot.exception.WarnException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ErrorAdvice {

  /**
   * 处理异常
   * <p>
   * 强制指定返回状态码: @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
   */
  @ExceptionHandler({ErrorException.class, WarnException.class})
  @ResponseBody
  public Map<String, Object> exceptionHandler(HttpServletResponse response, Exception e)
      throws Exception {
    if (e instanceof ErrorException) {
      log.error(null, e);
      response.setStatus(((ErrorException) e).getStatus().value());
    } else if (e instanceof WarnException) {
      response.setStatus(((WarnException) e).getStatus().value());
    } else {
      throw e;
    }
    return Collections
        .singletonMap("message", Objects.toString(e.getMessage(), e.getClass().getName()));
  }
}
