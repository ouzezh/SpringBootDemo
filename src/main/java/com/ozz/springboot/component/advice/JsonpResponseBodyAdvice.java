package com.ozz.springboot.component.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * 支持jsonp
 * 
 * 需要返回jsonp格式时在参数中加callback=?即可
 */
@ControllerAdvice(basePackages = "com.ozz.springboot.web")
public class JsonpResponseBodyAdvice extends AbstractJsonpResponseBodyAdvice {
  public JsonpResponseBodyAdvice() {
    super("callback", "jsonp");
  }
}