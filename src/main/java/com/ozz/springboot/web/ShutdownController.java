package com.ozz.springboot.web;

import com.ozz.springboot.exception.ErrorException;
import com.ozz.springboot.util.SpringUtils;
import java.io.IOException;
import javax.naming.OperationNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShutdownController {

  /**
   * 停止服务 效果同 kill {pid}
   */
  @PostMapping("/shutdown")
  public int shutdown(@RequestHeader("token") String token) throws IOException, OperationNotSupportedException {
    String TOKEN = "x";
    if(!TOKEN.equals(token)) {
      throw new ErrorException("403 token invalid");
    }
    SpringUtils.shutdownDelay(1000);
    return 0;
  }
}
