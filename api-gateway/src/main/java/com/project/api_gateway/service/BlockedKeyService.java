package com.project.api_gateway.service;

import com.project.api_gateway.constants.ApiGatewayConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BlockedKeyService {

    @Value("${api.block.cooldown-millis}")
    private long cooldownMillis;

    private final StringRedisTemplate stringRedisTemplate;

    public void block(String apiKey){
        stringRedisTemplate.opsForValue().set(
                ApiGatewayConstant.PREFIX+apiKey,
                "blocked",
                cooldownMillis,
                TimeUnit.MILLISECONDS
        );
        System.out.println("API key blocked in Redis: " + apiKey);
    }

    public boolean isBlocked(String apiKey){
      Boolean exists = stringRedisTemplate.hasKey(ApiGatewayConstant.PREFIX + apiKey);
      return exists!=null && exists;
    }



}
