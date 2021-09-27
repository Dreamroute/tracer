package com.github.dreamroute.tracer.starter;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.JSON.toJSONString;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author w.dehai.2021/9/9.14:58
 */
@Slf4j
@Aspect
@Configuration
@EnableConfigurationProperties(TracerProperties.class)
public class ArgsPrinterAutoConfiguration {

    @Resource
    private TracerProperties properties;

    @Bean
    public Advisor argsPrint() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        String packages = properties.getPackages();
        if (isEmpty(packages)) {
            throw new IllegalArgumentException("配置tracer.packages是必填项");
        }
        String execution = Arrays.stream(packages.split(",")).map(e -> "execution(* " + e.trim() + "..*.*(..))").collect(Collectors.joining(" || "));
        pointcut.setExpression(execution);

        MethodInterceptor interceptor = invocation -> {
            String methodName = invocation.getMethod().getDeclaringClass().getName() + "." + invocation.getMethod().getName();
            Object[] args = invocation.getArguments();
            log.info("方法: {}, 参数: {}", methodName, toJSONString(args));
            return invocation.proceed();
        };
        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}