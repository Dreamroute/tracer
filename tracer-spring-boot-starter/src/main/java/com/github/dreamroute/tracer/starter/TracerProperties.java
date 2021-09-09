package com.github.dreamroute.tracer.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author w.dehai.2021/8/23.10:10
 */
@Data
@ConfigurationProperties(prefix = "tracer")
public class TracerProperties {

    public static final String TRACE_ID = "traceId";

    /**
     * 是否开启trace id打印
     */
    private boolean enableTraceId = true;

    /**
     * 是否开启参数打印
     */
    private boolean enablePrintArgs = true;

    /**
     * 参数打印拦截的方法，为Spring AOP的表达式，
     * <p>
     *     单个例如: execution(public * com.example.disco.controller..*.*(..)) <br/>
     * </p>
     *<p>
     * 多个例如: execution(public * com.example.disco.controller..*.*(..)) || execution(public * com.example.disco.service.impl..*.*(..)) || execution(public * com.github.dreamroute.mybatis.pro.service.service..*.*(..)) || execution(public * com.example.disco.mapper..*.*(..))
     *</p>
     */
    private String executionExpression;


}
