package com.github.dreamroute.tracer.starter;

import org.slf4j.MDC;

import static com.github.dreamroute.tracer.starter.TracerProperties.TRACE_ID;

/**
 * @author w.dehai.2021/8/26.15:23
 */
public class TracerUtil {
    private TracerUtil() {}

    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    public static void clearTraceId() {
        MDC.remove(TRACE_ID);
    }
}
