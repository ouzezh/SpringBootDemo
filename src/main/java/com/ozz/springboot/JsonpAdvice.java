package com.ozz.springboot;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice(basePackages = "com.ozz.springboot.web")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
  public JsonpAdvice() {
    super("callback", "jsonp");
  }
}