package com.project.analytics_service.consumer;

import com.project.analytics_service.entity.ApiRequestLog;
import com.project.analytics_service.repository.ApiRequestLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class ApiRequestConsumer {

    private final ApiRequestLogRepository apiRequestLogRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "api-request-events", groupId = "analytics-group")
    public void consumer(String message){
        try{
            ApiRequestLog log = objectMapper.readValue(message,ApiRequestLog.class);
            apiRequestLogRepository.save(log);
            System.out.println("Saved event to DB: " + log);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
