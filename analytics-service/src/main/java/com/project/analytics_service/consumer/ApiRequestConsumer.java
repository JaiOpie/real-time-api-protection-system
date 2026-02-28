package com.project.analytics_service.consumer;

import com.project.analytics_service.entity.ApiRequestLog;
import com.project.analytics_service.repository.ApiRequestLogRepository;
import com.project.analytics_service.service.AbuseDetectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class ApiRequestConsumer {

    private final ApiRequestLogRepository apiRequestLogRepository;
    private final ObjectMapper objectMapper;
    private final AbuseDetectionService abuseDetectionService;

    @KafkaListener(topics = "api-request-events", groupId = "analytics-group")
    public void consumer(String message){
        try{
            ApiRequestLog log = objectMapper.readValue(message,ApiRequestLog.class);
            boolean isAbusive = abuseDetectionService.isAbsusive(log.getApiKey(),log.getTimestamp());

            if(isAbusive){
                System.out.println("ABUSE DETECTED FOR APIkey: " + log.getApiKey());
            }

            apiRequestLogRepository.save(log);
            System.out.println("Saved event to DB: " + log);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
