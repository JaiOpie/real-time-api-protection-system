package com.project.analytics_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "api_request_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiKey;
    private String path;
    private String method;
    private int status;
    private long responseTime;
    private long timestamp;
}
