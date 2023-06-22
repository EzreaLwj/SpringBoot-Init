package com.ezreal.autobi.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ezreal
 * @Date 2023/6/22
 */
@Configuration
@ConfigurationProperties(prefix = "redisson")
@Data
public class RedissonConfig {

    private String host;

    private String port;

    private String password;

    private Integer database;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();

        config.useSingleServer()
                .setPassword(password)
                .setDatabase(database)
                .setAddress("redis://" + host + ":" + port);

        return Redisson.create(config);
    }
}
