package com.ozz.springboot.component.filter;

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

import com.ozz.springboot.SampleApplication;
import com.ozz.springboot.dao.SampleDao;

@Component
public class SampleFilter implements Filter {

  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private SampleDao dao;

  public void init(FilterConfig filterConfig) throws ServletException {}

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    log.debug("do SampleFilter");
    if (dao == null) {
      dao = SampleApplication.getApplicationContext().getBean(SampleDao.class);
    }

    ParameterRequestWrapper w = setInfo(request);
    if (w == null) {
      chain.doFilter(request, response);
    } else {
      chain.doFilter(w, response);
    }
  }

  public void destroy() {}

  @SuppressWarnings({"unchecked", "rawtypes"})
  private ParameterRequestWrapper setInfo(ServletRequest request) {
    try {
      if (!(request instanceof HttpServletRequest)) {
        return null;
      }
      HashMap<String, String[]> m = new HashMap(request.getParameterMap());
      m.put("sampleAutosetParam", new String[] {"test"});
      ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper((HttpServletRequest) request, m);
      return wrapRequest;
    } catch (Exception e) {
      log.error(null, e);
      return null;
    }
  }

}
