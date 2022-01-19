package com.ozz.springboot.config.filter;

import com.ozz.springboot.service.MyService;
import com.ozz.springboot.util.LogUtil;
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

@Component
public class MyFilter implements Filter {

    @Autowired
    private MyService dao;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        LogUtil.log();
        if (dao == null) {
            dao = SpringUtils.getBean(MyService.class);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
