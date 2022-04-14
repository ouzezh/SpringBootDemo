package com.ozz.springboot.util;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class MdcUtil {
    public static final String KEY_TRACE_ID = "traceId";

    public static void generateTraceId() {
        try {
            MDC.put(KEY_TRACE_ID, IdUtil.fastSimpleUUID());
        } catch (Exception e) {
            log.error("generateTraceId fail");
        }
    }

    public static String getTraceId() {
        return MDC.get(KEY_TRACE_ID);
    }

    public static void clearTraceId() {
        MDC.remove(KEY_TRACE_ID);
    }

}
