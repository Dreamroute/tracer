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
     * 参数打印拦截的包
     * <p>
     * 单个例如: com.example.disco.controller <br/>
     * </p>
     * <p>
     * 多个用逗号分割，例如: com.example.disco.controller, com.example.disco.service.impl
     * </p>
     */
    private String packages;


}
