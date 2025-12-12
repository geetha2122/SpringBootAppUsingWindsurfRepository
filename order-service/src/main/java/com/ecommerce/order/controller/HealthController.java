package com.ecommerce.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Health check controller for Order Service.
 * 
 * @author E-commerce Team
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/actuator")
public class HealthController {

    /**
     * Health check endpoint.
     * 
     * @return health status
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        log.debug("Health check requested for Order Service");
        
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "order-service");
        health.put("version", "1.0.0");
        
        Map<String, Object> details = new HashMap<>();
        details.put("database", "UP");
        details.put("diskSpace", "UP");
        health.put("details", details);
        
        return ResponseEntity.ok(health);
    }

    /**
     * Service info endpoint.
     * 
     * @return service information
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        log.debug("Info requested for Order Service");
        
        Map<String, Object> info = new HashMap<>();
        info.put("name", "Order Service");
        info.put("description", "E-commerce Order Microservice");
        info.put("version", "1.0.0");
        info.put("port", "8082");
        info.put("status", "ACTIVE");
        
        return ResponseEntity.ok(info);
    }
}
