package com.project.api_gateway.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.common.events.ApiRequestEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiRequestProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.kafka.topic.api-requests}")
    private String topic;

    public void sendEvent(ApiRequestEvent event){
            try{
                String message = objectMapper.writeValueAsString(event);
                kafkaTemplate.send(topic, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

}
