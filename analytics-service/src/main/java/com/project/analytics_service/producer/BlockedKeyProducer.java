package com.project.analytics_service.producer;

import com.project.analytics_service.constants.AnalyticServiceConstant;
import com.project.common.events.BlockedKeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class BlockedKeyProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publishBlockedEvent(String apiKey){
        try{
            BlockedKeyEvent event = new BlockedKeyEvent(apiKey, System.currentTimeMillis());
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(AnalyticServiceConstant.TOPIC,message);

            System.out.println("Published block event for: " + apiKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
