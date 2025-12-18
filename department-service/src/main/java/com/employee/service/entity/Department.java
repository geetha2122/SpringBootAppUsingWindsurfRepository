package com.employee.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Department entity representing a department in the organization.
 * 
 * @author Department Service Team
 * @version 1.0.0
 * @since 2025-12-12
 */
@Entity
@Table(name = "departments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Department name is required")
    @Size(max = 100, message = "Department name must not exceed 100 characters")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(name = "description")
    private String description;

    @NotBlank(message = "Department code is required")
    @Size(max = 10, message = "Department code must not exceed 10 characters")
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "manager_name")
    @Size(max = 100, message = "Manager name must not exceed 100 characters")
    private String managerName;

    @Column(name = "manager_email")
    @Size(max = 100, message = "Manager email must not exceed 100 characters")
    private String managerEmail;

    @Column(name = "location")
    @Size(max = 200, message = "Location must not exceed 200 characters")
    private String location;

    @Column(name = "budget")
    private Double budget;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
