package com.employee.service.controller;

import com.employee.service.dto.EmployeeDto;
import com.employee.service.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Employee operations.
 * 
 * @author Employee Service Team
 * @version 1.0.0
 * @since 2025-12-12
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Create a new employee.
     * 
     * @param employeeDto the employee data
     * @return the created employee
     */
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        log.info("REST request to create employee: {}", employeeDto.getEmail());
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    /**
     * Get employee by ID.
     * 
     * @param id the employee ID
     * @return the employee if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        log.info("REST request to get employee with ID: {}", id);
        return employeeService.getEmployeeById(id)
                .map(employee -> ResponseEntity.ok().body(employee))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all employees.
     * 
     * @return list of all employees
     */
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        log.info("REST request to get all employees");
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * Update an existing employee.
     * 
     * @param id the employee ID
     * @param employeeDto the updated employee data
     * @return the updated employee
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, 
                                                     @Valid @RequestBody EmployeeDto employeeDto) {
        log.info("REST request to update employee with ID: {}", id);
        try {
            EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDto);
            return ResponseEntity.ok(updatedEmployee);
        } catch (IllegalArgumentException e) {
            log.error("Error updating employee: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete an employee.
     * 
     * @param id the employee ID
     * @return no content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("REST request to delete employee with ID: {}", id);
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Error deleting employee: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get employees by department ID.
     * 
     * @param departmentId the department ID
     * @return list of employees in the department
     */
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeDto>> getEmployeesByDepartment(@PathVariable Long departmentId) {
        log.info("REST request to get employees for department ID: {}", departmentId);
        List<EmployeeDto> employees = employeeService.getEmployeesByDepartment(departmentId);
        return ResponseEntity.ok(employees);
    }

    /**
     * Get active employees.
     * 
     * @return list of active employees
     */
    @GetMapping("/active")
    public ResponseEntity<List<EmployeeDto>> getActiveEmployees() {
        log.info("REST request to get active employees");
        List<EmployeeDto> employees = employeeService.getActiveEmployees();
        return ResponseEntity.ok(employees);
    }
}
