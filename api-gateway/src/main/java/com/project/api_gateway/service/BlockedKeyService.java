package com.project.api_gateway.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BlockedKeyService {

    private final long cooldownMillis;

    private final Map<String, Long> blockedKeys = new ConcurrentHashMap<>();

    public BlockedKeyService(
            @Value("${api.block.cooldown-millis}") long cooldownMillis) {
        this.cooldownMillis = cooldownMillis;
    }

    public void block(String apiKey){
        blockedKeys.put(apiKey,System.currentTimeMillis());
    }

    public boolean isBlocked(String apiKey){
        if(!blockedKeys.containsKey(apiKey)) return false;

        long blockedAt = blockedKeys.get(apiKey);
        long now = System.currentTimeMillis();

        if(now - blockedAt > cooldownMillis){
            blockedKeys.remove(apiKey);
            System.out.println("Auto-unblocked API key: " + apiKey);
            return false;
        }
        return true;
    }



}
