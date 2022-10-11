package com.github.dreamroute.tracer.sample.consumer.controller;

import com.github.dreamroute.tracer.starter.Tracer;
import com.github.dreamroute.tracer.sample.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

//    @Resource
//    private ExecutorService pool;

    private final Tracer tracer;

    @DubboReference
    private UserService userService;

    @GetMapping("/index")
    public String index() {
        log.info("OUTER");
        userService.selectById(20L);
//        pool.submit(() -> {
//          log.info("INNER");
//        });
//
//        Map<String, String> map = new HashMap<>();
//        map.put("_name", "_w.dehai");
//        tracer.set(map);
//
//        pool.submit(() -> {
//            String name = MDC.get("_name");
//            System.err.println(name);
//        });

        return "ok";
    }

}
