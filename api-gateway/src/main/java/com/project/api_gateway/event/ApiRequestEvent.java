package com.project.api_gateway.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRequestEvent {

    private String apiKey;
    private String path;
    private String method;
    private int status;
    private long responseTime;
    private long timestamp;
}