package com.ozz.springboot.web;

import com.ozz.springboot.config.context.SpringUtils;
import com.ozz.springboot.exception.ErrorException;
import java.io.IOException;
import javax.naming.OperationNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShutdownController {
  @PostMapping("/myShutdown")
  public int shutdown() {
    try {
      return SpringUtils.shutdown();
    } catch (IOException | OperationNotSupportedException e) {
      throw new ErrorException(e);
    }
  }
}
