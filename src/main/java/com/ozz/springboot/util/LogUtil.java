package com.ozz.springboot.util;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtil {
    public static void log() {
        StackTraceElement st = ExceptionUtil.getStackElement(3);
        log.info("{}.{}", st.getClassName(), st.getMethodName());
    }

    public static void log(String msg) {
        StackTraceElement st = ExceptionUtil.getStackElement(3);
        log.info("{}.{} {}", st.getClassName(), st.getMethodName(), msg);
    }

}
