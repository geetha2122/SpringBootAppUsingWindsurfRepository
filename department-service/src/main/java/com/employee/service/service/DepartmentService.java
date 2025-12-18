package com.employee.service.service;

import com.employee.service.dto.DepartmentDto;
import com.employee.service.entity.Department;
import com.employee.service.repository.DepartmentRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for Department business logic.
 * 
 * @author Department Service Team
 * @version 1.0.0
 * @since 2025-12-12
 */
@Slf4j
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Create a new department.
     * 
     * @param departmentDto the department data
     * @return the created department
     */
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        log.info("Creating new department: {}", departmentDto.getName());
        
        if (departmentRepository.existsByName(departmentDto.getName())) {
            throw new IllegalArgumentException("Department with name " + departmentDto.getName() + " already exists");
        }

        if (departmentRepository.existsByCode(departmentDto.getCode())) {
            throw new IllegalArgumentException("Department with code " + departmentDto.getCode() + " already exists");
        }

        Department department = Department.builder()
                .name(departmentDto.getName())
                .description(departmentDto.getDescription())
                .code(departmentDto.getCode())
                .managerName(departmentDto.getManagerName())
                .managerEmail(departmentDto.getManagerEmail())
                .location(departmentDto.getLocation())
                .budget(departmentDto.getBudget())
                .isActive(departmentDto.getIsActive() != null ? departmentDto.getIsActive() : true)
                .createdAt(LocalDate.now())
                .build();

        Department savedDepartment = departmentRepository.save(department);
        log.info("Department created successfully with ID: {}", savedDepartment.getId());
        
        return convertToDto(savedDepartment);
    }

    /**
     * Get department by ID.
     * 
     * @param id the department ID
     * @return the department if found
     */
    public Optional<DepartmentDto> getDepartmentById(Long id) {
        log.debug("Fetching department with ID: {}", id);
        return departmentRepository.findById(id)
                .map(this::convertToDto);
    }

    /**
     * Get all departments.
     * 
     * @return list of all departments
     */
    public List<DepartmentDto> getAllDepartments() {
        log.debug("Fetching all departments");
        return departmentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing department.
     * 
     * @param id the department ID
     * @param departmentDto the updated department data
     * @return the updated department
     */
    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) {
        log.info("Updating department with ID: {}", id);
        
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + id));

        if (!existingDepartment.getName().equals(departmentDto.getName()) && 
            departmentRepository.existsByName(departmentDto.getName())) {
            throw new IllegalArgumentException("Department with name " + departmentDto.getName() + " already exists");
        }

        if (!existingDepartment.getCode().equals(departmentDto.getCode()) && 
            departmentRepository.existsByCode(departmentDto.getCode())) {
            throw new IllegalArgumentException("Department with code " + departmentDto.getCode() + " already exists");
        }

        existingDepartment.setName(departmentDto.getName());
        existingDepartment.setDescription(departmentDto.getDescription());
        existingDepartment.setCode(departmentDto.getCode());
        existingDepartment.setManagerName(departmentDto.getManagerName());
        existingDepartment.setManagerEmail(departmentDto.getManagerEmail());
        existingDepartment.setLocation(departmentDto.getLocation());
        existingDepartment.setBudget(departmentDto.getBudget());
        existingDepartment.setIsActive(departmentDto.getIsActive());
        existingDepartment.setUpdatedAt(LocalDate.now());

        Department updatedDepartment = departmentRepository.save(existingDepartment);
        log.info("Department updated successfully with ID: {}", updatedDepartment.getId());
        
        return convertToDto(updatedDepartment);
    }

    /**
     * Delete a department.
     * 
     * @param id the department ID
     */
    public void deleteDepartment(Long id) {
        log.info("Deleting department with ID: {}", id);
        
        if (!departmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Department not found with ID: " + id);
        }
        
        departmentRepository.deleteById(id);
        log.info("Department deleted successfully with ID: {}", id);
    }

    /**
     * Get departments by location.
     * 
     * @param location the location
     * @return list of departments in the location
     */
    public List<DepartmentDto> getDepartmentsByLocation(String location) {
        log.debug("Fetching departments for location: {}", location);
        return departmentRepository.findByLocation(location).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get active departments.
     * 
     * @return list of active departments
     */
    public List<DepartmentDto> getActiveDepartments() {
        log.debug("Fetching active departments");
        return departmentRepository.findByIsActive(true).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Search departments by name.
     * 
     * @param name the name fragment
     * @return list of departments matching the name fragment
     */
    public List<DepartmentDto> searchDepartmentsByName(String name) {
        log.debug("Searching departments with name containing: {}", name);
        return departmentRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get department by code.
     * 
     * @param code the department code
     * @return the department if found
     */
    public Optional<DepartmentDto> getDepartmentByCode(String code) {
        log.debug("Fetching department with code: {}", code);
        return departmentRepository.findByCode(code)
                .map(this::convertToDto);
    }

    /**
     * Convert Department entity to DepartmentDto.
     * 
     * @param department the department entity
     * @return the department DTO
     */
    private DepartmentDto convertToDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .code(department.getCode())
                .managerName(department.getManagerName())
                .managerEmail(department.getManagerEmail())
                .location(department.getLocation())
                .budget(department.getBudget())
                .isActive(department.getIsActive())
                .createdAt(department.getCreatedAt())
                .updatedAt(department.getUpdatedAt())
                .build();
    }
}
