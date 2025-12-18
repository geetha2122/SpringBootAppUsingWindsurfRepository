package com.employee.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Data Transfer Object for Department entity.
 * 
 * @author Department Service Team
 * @version 1.0.0
 * @since 2025-12-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    private Long id;

    @NotBlank(message = "Department name is required")
    @Size(max = 100, message = "Department name must not exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "Department code is required")
    @Size(max = 10, message = "Department code must not exceed 10 characters")
    private String code;

    @Size(max = 100, message = "Manager name must not exceed 100 characters")
    private String managerName;

    @Size(max = 100, message = "Manager email must not exceed 100 characters")
    private String managerEmail;

    @Size(max = 200, message = "Location must not exceed 200 characters")
    private String location;

    private Double budget;

    private Boolean isActive;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private Long employeeCount;
}
