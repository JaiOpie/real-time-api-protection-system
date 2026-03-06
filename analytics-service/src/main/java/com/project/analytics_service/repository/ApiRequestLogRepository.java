package com.project.analytics_service.repository;

import com.project.analytics_service.entity.ApiRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiRequestLogRepository extends JpaRepository<ApiRequestLog, Long> {
    List<ApiRequestLog> findTop100ByOrderByTimestampDesc();
    @Query("SELECT DISTINCT a.apiKey FROM ApiRequestLog a WHERE a.status = 429")
    List<String> findDistinctBlockedKeys();
}