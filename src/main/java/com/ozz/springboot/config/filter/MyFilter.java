package com.ozz.springboot.config.filter;

import com.ozz.springboot.service.MyService;
import com.ozz.springboot.util.SpringUtils;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyFilter implements Filter {

  @Autowired
  private MyService dao;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    log.debug("do myFilter");
    if (dao == null) {
      dao = SpringUtils.getBean(MyService.class);
    }

    ParameterRequestWrapper w = setInfo(request);
    if (w == null) {
      chain.doFilter(request, response);
    } else {
      chain.doFilter(w, response);
    }
  }

  @Override
  public void destroy() {
  }

  private ParameterRequestWrapper setInfo(ServletRequest request) {
    try {
      String contentType = "application/x-www-form-urlencoded";
      if (request instanceof HttpServletRequest && contentType.equals(request.getContentType())) {
        HashMap<String, String[]> m = new HashMap(request.getParameterMap());
        m.put("myAutoSetParam", new String[] {"test"});
        ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper((HttpServletRequest) request, m);
        return wrapRequest;
      }
      return null;
    } catch (Exception e) {
      log.error(null, e);
      return null;
    }
  }

}
