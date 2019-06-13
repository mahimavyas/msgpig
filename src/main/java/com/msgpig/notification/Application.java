package com.msgpig.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class Application {

    public static void main(String[] args) {
    	java.security.Security.setProperty("networkaddress.cache.ttl" , "10");
        SpringApplication.run(Application.class, args);
    }
}
