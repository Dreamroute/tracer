package com.github.dreamroute.tracer.sample.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        System.setProperty("dubbo.application.logger", "slf4j");
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
