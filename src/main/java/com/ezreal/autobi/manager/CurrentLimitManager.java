package com.ezreal.autobi.manager;

import com.ezreal.autobi.exception.ToManyRequestException;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Ezreal
 * @Date 2023/6/22
 */
@Component
public class CurrentLimitManager {

    @Resource
    private RedissonClient redissonClient;

    public void doRateLimit(String key) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);

        // 每秒钟最多访问两次
        rateLimiter.trySetRate(RateType.OVERALL, 2, 1, RateIntervalUnit.SECONDS);

        boolean acquire = rateLimiter.tryAcquire(1);
        if (!acquire) {
            throw new ToManyRequestException("to many request");
        }
    }
}
