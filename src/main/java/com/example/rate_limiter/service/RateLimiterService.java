package com.example.rate_limiter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimiterService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final int MAX_REQUESTS=10;
    private static final int WINDOW_TIME_SECONDS=60; //10 reuqests per second

    public boolean isAllowed(String userId){
        String key = "rate_limit:"+userId;

        Long count= redisTemplate.opsForValue().increment(key); //keeping count for the user

        if(count != null && count==1 ){
            redisTemplate.expire(key, WINDOW_TIME_SECONDS, TimeUnit.SECONDS);
        }

        if(count!=null && count > MAX_REQUESTS){
            return false;
        }
        return true;

    }
}
