package com.github.dreamroute.tracer.sample.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {

        System.setProperty("dubbo.application.logger", "slf4j");
        SpringApplication app = new SpringApplication(ProviderApplication.class);
                app.setWebApplicationType(WebApplicationType.NONE);
                app.run(args);
    }

}
