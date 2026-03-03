package com.project.api_gateway.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BlockedKeyService {

    private final Set<String> blockedKeys = ConcurrentHashMap.newKeySet();

    public void block(String apiKey){
        blockedKeys.add(apiKey);
    }

    public boolean isBlocked(String apiKey){
        return blockedKeys.contains(apiKey);
    }

}
