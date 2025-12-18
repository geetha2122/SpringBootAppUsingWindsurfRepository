package com.employee.service.controller;

import com.employee.service.dto.DepartmentDto;
import com.employee.service.service.DepartmentService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Department operations.
 * 
 * @author Department Service Team
 * @version 1.0.0
 * @since 2025-12-12
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/departments")
@CrossOrigin(origins = "*")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Create a new department.
     * 
     * @param departmentDto the department data
     * @return the created department
     */
    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
        log.info("REST request to create department: {}", departmentDto.getName());
        DepartmentDto createdDepartment = departmentService.createDepartment(departmentDto);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    /**
     * Get department by ID.
     * 
     * @param id the department ID
     * @return the department if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        log.info("REST request to get department with ID: {}", id);
        return departmentService.getDepartmentById(id)
                .map(department -> ResponseEntity.ok().body(department))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all departments.
     * 
     * @return list of all departments
     */
    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        log.info("REST request to get all departments");
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    /**
     * Update an existing department.
     * 
     * @param id the department ID
     * @param departmentDto the updated department data
     * @return the updated department
     */
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable Long id, 
                                                         @Valid @RequestBody DepartmentDto departmentDto) {
        log.info("REST request to update department with ID: {}", id);
        try {
            DepartmentDto updatedDepartment = departmentService.updateDepartment(id, departmentDto);
            return ResponseEntity.ok(updatedDepartment);
        } catch (IllegalArgumentException e) {
            log.error("Error updating department: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a department.
     * 
     * @param id the department ID
     * @return no content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        log.info("REST request to delete department with ID: {}", id);
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Error deleting department: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get departments by location.
     * 
     * @param location the location
     * @return list of departments in the location
     */
    @GetMapping("/location/{location}")
    public ResponseEntity<List<DepartmentDto>> getDepartmentsByLocation(@PathVariable String location) {
        log.info("REST request to get departments for location: {}", location);
        List<DepartmentDto> departments = departmentService.getDepartmentsByLocation(location);
        return ResponseEntity.ok(departments);
    }

    /**
     * Get active departments.
     * 
     * @return list of active departments
     */
    @GetMapping("/active")
    public ResponseEntity<List<DepartmentDto>> getActiveDepartments() {
        log.info("REST request to get active departments");
        List<DepartmentDto> departments = departmentService.getActiveDepartments();
        return ResponseEntity.ok(departments);
    }

    /**
     * Search departments by name.
     * 
     * @param name the name fragment
     * @return list of departments matching the name fragment
     */
    @GetMapping("/search")
    public ResponseEntity<List<DepartmentDto>> searchDepartmentsByName(@RequestParam String name) {
        log.info("REST request to search departments with name: {}", name);
        List<DepartmentDto> departments = departmentService.searchDepartmentsByName(name);
        return ResponseEntity.ok(departments);
    }

    /**
     * Get department by code.
     * 
     * @param code the department code
     * @return the department if found
     */
    @GetMapping("/code/{code}")
    public ResponseEntity<DepartmentDto> getDepartmentByCode(@PathVariable String code) {
        log.info("REST request to get department with code: {}", code);
        return departmentService.getDepartmentByCode(code)
                .map(department -> ResponseEntity.ok().body(department))
                .orElse(ResponseEntity.notFound().build());
    }
}
