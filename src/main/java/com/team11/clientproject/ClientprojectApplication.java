package com.team11.clientproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class ClientprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientprojectApplication.class, args);
    }
}
