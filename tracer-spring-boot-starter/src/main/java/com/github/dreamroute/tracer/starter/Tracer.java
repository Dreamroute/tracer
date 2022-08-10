package com.github.dreamroute.tracer.starter;

import ch.qos.logback.classic.util.LogbackMDCAdapter;
import com.alibaba.ttl.TransmittableThreadLocal;
import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;

import java.util.Map;
import java.util.Set;

import static cn.hutool.core.util.ReflectUtil.getFieldValue;
import static cn.hutool.core.util.ReflectUtil.setFieldValue;
import static org.slf4j.MDC.getMDCAdapter;

/**
 * 描述：tracer工具类
 *
 * @author w.dehai
 */
public class Tracer {

    private static final String NAME = "copyOnThreadLocal";

    /**
     * 批量设置k，v
     *
     * @param kv 需要设置的k，v
     */
    public void set(Map<String, String> kv) {
        MDCAdapter mdcAdapter = getMDCAdapter();
        Object tl = getFieldValue(mdcAdapter, NAME);
        if (!(tl instanceof TransmittableThreadLocal)) {
            setFieldValue(mdcAdapter, NAME, new TransmittableThreadLocal<>());
        }
        if (kv != null && !kv.isEmpty()) {
            kv.forEach(MDC::put);
        }
    }

    /**
     * 获取MDC的全部key
     */
    public Set<String> getKeys() {
        return ((LogbackMDCAdapter) getMDCAdapter()).getKeys();
    }

    /**
     * 根据k获取MDC的value
     */
    public String get(String key) {
        return MDC.get(key);
    }

    /**
     * 清除所有的自定义key
     */
    public void clear() {
        Set<String> keys = getKeys();
        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                MDC.remove(key);
            }
        }
    }

}
