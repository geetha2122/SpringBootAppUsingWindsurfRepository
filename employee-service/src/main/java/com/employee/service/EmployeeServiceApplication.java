package com.employee.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main application class for Employee Service.
 * 
 * @author Employee Service Team
 * @version 1.0.0
 * @since 2025-12-12
 */
@SpringBootApplication
@EnableFeignClients
public class EmployeeServiceApplication {

    /**
     * Main method to start the Employee Service application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }
}
