package com.github.dreamroute.tracer.sample.consumer.controller;

import com.github.dreamroute.tracer.sample.api.service.UserService;
import com.github.dreamroute.tracer.starter.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    // 如果不使用ttl，那么就是使用的是InheritableThreadLocal，下方的hotPool()不预热可能会看到正确的数据，但是一旦进行了预热，那么就无法传递参数了
    private final ExecutorService pool;
    private final Tracer tracer;

    @DubboReference
    private UserService userService;

    @GetMapping("/index")
    public String index() {

        hotPool();

        log.info("OUTER");
        userService.selectById(20L);
        pool.submit(() -> {
          log.info("INNER");
        });

        Map<String, String> map = new HashMap<>();
        map.put("_name", "_w.dehai");
        tracer.set(map);

        pool.submit(() -> {
            String name = MDC.get("_name");
            System.err.println(name);
        });

        return "ok";
    }

    /**
     * 预热pool，避免请求到位了才创建线程
     */
    private void hotPool() {
        for (int i=0; i<100; i++)
            pool.submit(() -> {});
    }

}
