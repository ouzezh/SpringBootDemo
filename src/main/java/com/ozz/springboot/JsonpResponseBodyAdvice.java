package com.ozz.springboot;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice(basePackages = "com.ozz.springboot.web")
public class JsonpResponseBodyAdvice extends AbstractJsonpResponseBodyAdvice {
  public JsonpResponseBodyAdvice() {
    super("callback", "jsonp");
  }
}