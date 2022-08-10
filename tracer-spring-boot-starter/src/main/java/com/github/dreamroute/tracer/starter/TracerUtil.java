package com.github.dreamroute.tracer.starter;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.slf4j.spi.MDCAdapter;

import static cn.hutool.core.util.ReflectUtil.getFieldValue;
import static cn.hutool.core.util.ReflectUtil.setFieldValue;
import static com.github.dreamroute.tracer.starter.TracerProperties.TRACE_ID;
import static org.slf4j.MDC.get;
import static org.slf4j.MDC.getMDCAdapter;
import static org.slf4j.MDC.put;
import static org.slf4j.MDC.remove;

/**
 * trace id工具类，用于包装SLF4J的MDC
 *
 * @author w.dehai.2021/8/26.15:23
 */
public class TracerUtil {
    private TracerUtil() {}

    private static final String NAME = "copyOnThreadLocal";

    public static void setTraceId(String traceId) {
        MDCAdapter mdcAdapter = getMDCAdapter();
        Object tl = getFieldValue(mdcAdapter, NAME);
        if (!(tl instanceof TransmittableThreadLocal)) {
            setFieldValue(mdcAdapter, NAME, new TransmittableThreadLocal<>());
        }
        put(TRACE_ID, traceId);
    }

    public static String getTraceId() {
        return get(TRACE_ID);
    }

    public static void clearTraceId() {
        remove(TRACE_ID);
    }
}
