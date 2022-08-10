package com.github.dreamroute.tracer.starter;

import org.springframework.context.annotation.Bean;

/**
 * 描述：springboot初始化类型定义
 */
public class TracerAutoConfiguration {

    @Bean
    public Tracer tracer() {
        return new Tracer();
    }

}
