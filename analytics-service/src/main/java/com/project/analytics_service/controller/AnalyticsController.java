package com.project.analytics_service.controller;

import com.project.analytics_service.entity.ApiRequestLog;
import com.project.analytics_service.repository.ApiRequestLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AnalyticsController {

    private final ApiRequestLogRepository apiRequestLogRepository;

    @GetMapping("/requests")
    public List<ApiRequestLog> getAllRequests(){
        return apiRequestLogRepository.findTop100ByOrderByTimestampDesc();
    }

    @GetMapping("/blocked-keys")
    public List<String> getBlockedKeys() {
        return apiRequestLogRepository.findDistinctBlockedKeys();
    }

}
