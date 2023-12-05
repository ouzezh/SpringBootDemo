package com.ozz.springboot.config.filter;

import com.ozz.springboot.util.MdcUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            MdcUtil.generateTraceId();
            if(response instanceof HttpServletResponse) {
                ((HttpServletResponse) response).setHeader(MdcUtil.KEY_TRACE_ID, MdcUtil.getTraceId());
            }
            chain.doFilter(request, response);
        } finally {
            MdcUtil.clearTraceId();
        }
    }

    @Override
    public void destroy() {
    }

}
