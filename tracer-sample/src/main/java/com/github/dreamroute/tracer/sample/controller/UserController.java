package com.github.dreamroute.tracer.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private ExecutorService pool;

    @GetMapping("/index")
    public String index() {
        log.info("OUTER");
        pool.submit(() -> {
          log.info("INNER");
        });
        return "ok";
    }

}
