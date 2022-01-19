package com.ozz.springboot.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtil {
    public static void log() {
        StackTraceElement st = Thread.currentThread().getStackTrace()[2];
        log.info("{}.{}", st.getClassName(), st.getMethodName());
    }

    public static void log(String msg) {
        StackTraceElement st = Thread.currentThread().getStackTrace()[2];
        log.info("{}.{} {}", st.getClassName(), st.getMethodName(), msg);
    }
}
