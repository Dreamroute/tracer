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
    private static final TransmittableThreadLocal<Map<String, String>> TARGET = new TransmittableThreadLocal<>(); // 定义成Tracer的类属性，避免被弱引用GC掉

    /**
     * 批量设置k，v
     *
     * @param kv 需要设置的k，v
     */
    public void set(Map<String, String> kv) {
        TARGET.remove(); // 避免线程池中的线程属性未被remove掉，再此入口处首先remove一下确保线程池中的线程此属性无旧数据
        MDCAdapter mdcAdapter = getMDCAdapter();
        Object tl = getFieldValue(mdcAdapter, NAME);
        if (!(tl instanceof TransmittableThreadLocal)) {
            ThreadLocal<Map<String, String>> copy = (ThreadLocal<Map<String, String>>) tl;
            Map<String, String> map = copy.get();
            if (map != null) {
                TARGET.set(map);
            }
            setFieldValue(mdcAdapter, NAME, TARGET);
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
