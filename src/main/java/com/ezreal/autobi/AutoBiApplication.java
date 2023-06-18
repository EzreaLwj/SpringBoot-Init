package com.ezreal.autobi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
public class AutoBiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoBiApplication.class, args);
    }

}
