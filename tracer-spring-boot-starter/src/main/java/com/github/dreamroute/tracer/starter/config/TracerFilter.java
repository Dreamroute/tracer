package com.github.dreamroute.tracer.starter.config;

import org.slf4j.MDC;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.UUID;

import static com.github.dreamroute.tracer.starter.config.TracerProperties.TRACE_ID;

/**
 * @author w.dehai.2021/8/24.15:32
 */
public class TracerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put(TRACE_ID, UUID.randomUUID().toString().replace("-", ""));
    }
}
