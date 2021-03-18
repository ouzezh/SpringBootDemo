package com.ozz.springboot.config.filter;

import com.ozz.springboot.config.context.SpringUtils;
import com.ozz.springboot.service.MyService;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyFilter implements Filter {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private MyService dao;

  public void init(FilterConfig filterConfig) throws ServletException {}

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

  public void destroy() {}

  private ParameterRequestWrapper setInfo(ServletRequest request) {
    try {
      if (request instanceof HttpServletRequest && "application/x-www-form-urlencoded".equals(request.getContentType())) {
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
