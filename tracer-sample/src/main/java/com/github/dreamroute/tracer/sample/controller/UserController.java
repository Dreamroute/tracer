package com.github.dreamroute.tracer.sample.controller;

import com.github.dreamroute.tracer.starter.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private ExecutorService pool;

    @Resource
    private Tracer tracer;

    @GetMapping("/index")
    public String index() {
        log.info("OUTER");
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

}
