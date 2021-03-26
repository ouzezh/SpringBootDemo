package com.ozz.springboot.web;

import com.ozz.springboot.util.SpringUtils;
import java.io.IOException;
import javax.naming.OperationNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShutdownController {

  @PostMapping("/shutdown")
  public int shutdown() throws IOException, OperationNotSupportedException {
    SpringUtils.shutdownDelay(1000);
    return 0;
  }
}
