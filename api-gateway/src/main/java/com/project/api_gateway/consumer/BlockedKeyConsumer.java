package com.project.api_gateway.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api_gateway.service.BlockedKeyService;
import com.project.common.events.BlockedKeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockedKeyConsumer {

    private final BlockedKeyService blockedKeyService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "blocked-api-keys", groupId = "gateway-group")
    public void consume(String message){
        try{
            BlockedKeyEvent event = objectMapper.readValue(message,BlockedKeyEvent.class);
            blockedKeyService.block(event.getApiKey());

            System.out.println("Gateway blocked API key: " + event.getApiKey());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
