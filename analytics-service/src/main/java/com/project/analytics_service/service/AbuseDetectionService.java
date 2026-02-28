package com.project.analytics_service.service;

import com.project.analytics_service.constants.AnalyticServiceConstant;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class AbuseDetectionService {

    private static final Map<String, Queue<Long>> requestTracker = new ConcurrentHashMap<>();

    public boolean isAbsusive(String apiKey, long currentTimestamp){
        requestTracker.putIfAbsent(apiKey,new ConcurrentLinkedQueue<>());
        Queue<Long> timestamps = requestTracker.get(apiKey);
        timestamps.add(currentTimestamp);

        while(!timestamps.isEmpty()
                & currentTimestamp-timestamps.peek()> AnalyticServiceConstant.TIME_WINDOW_MS){
            timestamps.poll();
        }
        return timestamps.size()>AnalyticServiceConstant.REQUEST_LIMIT;
    }

}
