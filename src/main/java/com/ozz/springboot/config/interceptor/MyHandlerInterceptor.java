package com.ozz.springboot.config.interceptor;

import cn.hutool.core.util.StrUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 拦截器：由Spring调用 ，能实现Filter的功能，可以IoC注入Spring里的资源，AOP的一种实现策略
 */
@Component
public class MyHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        LoginUserContext.set();

        // 跨域 CORS
        String origin = request.getHeader("Origin");
        if(StrUtil.isNotEmpty(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers",
                    Optional.ofNullable(request.getHeader("Access-Control-Request-Headers")).map(StrUtil::emptyToNull).orElse("*"));
        }

        return true;
    }

    /**
     * 视图渲染后执行，多用于清理资源
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
//        LoginUserContext.clear();
    }
}
