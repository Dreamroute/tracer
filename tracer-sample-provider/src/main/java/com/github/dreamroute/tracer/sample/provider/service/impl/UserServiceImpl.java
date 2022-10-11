package com.github.dreamroute.tracer.sample.provider.service.impl;

import com.github.dreamroute.tracer.sample.api.domain.User;
import com.github.dreamroute.tracer.sample.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;

@Slf4j
@DubboService
public class UserServiceImpl implements UserService {

    @Resource
    private ThreadPoolTaskExecutor pool;

    @Override
    public User selectById(Long id) {
        User user = new User();
        user.setId(id);
        log.info("消费端打印日志");

        MDC.put("age", "20");

        pool.submit(() -> {
            log.info("ddd");
            System.err.println(MDC.get("age"));
        });
        return user;
    }
}