package com.github.dreamroute.tracer.sample.config;

import cn.hutool.core.lang.UUID;
import com.github.dreamroute.tracer.starter.Tracer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Order(1)
@Component
public class TracerFilter implements Filter {

    @Resource
    private Tracer tracer;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Map<String, String> map = new HashMap<>();
        map.put("traceId", UUID.fastUUID().toString(true));
        map.put("traceUserId", UUID.fastUUID().toString(true));
        map.put("traceLogId", UUID.fastUUID().toString(true));
        tracer.set(map);
        filterChain.doFilter(servletRequest, servletResponse);
        tracer.clear();
    }
}
