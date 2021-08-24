package com.github.dreamroute.tracer.starter.config;

import lombok.Data;

/**
 * @author w.dehai.2021/8/23.10:10
 */
@Data
public class TracerProperties {
    public static final String TRACE_ID = "traceId";

    private String traceId = TRACE_ID;
}
