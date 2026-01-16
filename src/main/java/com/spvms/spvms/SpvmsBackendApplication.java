package com.spvms.spvms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpvmsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpvmsBackendApplication.class, args);
    }
}
