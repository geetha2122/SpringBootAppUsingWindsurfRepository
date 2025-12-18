package com.employee.service.service;

import com.employee.service.dto.EmployeeDto;
import com.employee.service.entity.Employee;
import com.employee.service.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for Employee business logic.
 * 
 * @author Employee Service Team
 * @version 1.0.0
 * @since 2025-12-12
 */
@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Create a new employee.
     * 
     * @param employeeDto the employee data
     * @return the created employee
     */
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        log.info("Creating new employee: {}", employeeDto.getEmail());
        
        if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
            throw new IllegalArgumentException("Employee with email " + employeeDto.getEmail() + " already exists");
        }

        Employee employee = Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .email(employeeDto.getEmail())
                .phoneNumber(employeeDto.getPhoneNumber())
                .departmentId(employeeDto.getDepartmentId())
                .position(employeeDto.getPosition())
                .hireDate(employeeDto.getHireDate())
                .salary(employeeDto.getSalary())
                .isActive(employeeDto.getIsActive() != null ? employeeDto.getIsActive() : true)
                .createdAt(LocalDate.now())
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee created successfully with ID: {}", savedEmployee.getId());
        
        return convertToDto(savedEmployee);
    }

    /**
     * Get employee by ID.
     * 
     * @param id the employee ID
     * @return the employee if found
     */
    public Optional<EmployeeDto> getEmployeeById(Long id) {
        log.debug("Fetching employee with ID: {}", id);
        return employeeRepository.findById(id)
                .map(this::convertToDto);
    }

    /**
     * Get all employees.
     * 
     * @return list of all employees
     */
    public List<EmployeeDto> getAllEmployees() {
        log.debug("Fetching all employees");
        return employeeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Update an existing employee.
     * 
     * @param id the employee ID
     * @param employeeDto the updated employee data
     * @return the updated employee
     */
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        log.info("Updating employee with ID: {}", id);
        
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));

        if (!existingEmployee.getEmail().equals(employeeDto.getEmail()) && 
            employeeRepository.existsByEmail(employeeDto.getEmail())) {
            throw new IllegalArgumentException("Employee with email " + employeeDto.getEmail() + " already exists");
        }

        existingEmployee.setFirstName(employeeDto.getFirstName());
        existingEmployee.setLastName(employeeDto.getLastName());
        existingEmployee.setEmail(employeeDto.getEmail());
        existingEmployee.setPhoneNumber(employeeDto.getPhoneNumber());
        existingEmployee.setDepartmentId(employeeDto.getDepartmentId());
        existingEmployee.setPosition(employeeDto.getPosition());
        existingEmployee.setSalary(employeeDto.getSalary());
        existingEmployee.setIsActive(employeeDto.getIsActive());
        existingEmployee.setUpdatedAt(LocalDate.now());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        log.info("Employee updated successfully with ID: {}", updatedEmployee.getId());
        
        return convertToDto(updatedEmployee);
    }

    /**
     * Delete an employee.
     * 
     * @param id the employee ID
     */
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);
        
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee not found with ID: " + id);
        }
        
        employeeRepository.deleteById(id);
        log.info("Employee deleted successfully with ID: {}", id);
    }

    /**
     * Get employees by department ID.
     * 
     * @param departmentId the department ID
     * @return list of employees in the department
     */
    public List<EmployeeDto> getEmployeesByDepartment(Long departmentId) {
        log.debug("Fetching employees for department ID: {}", departmentId);
        return employeeRepository.findByDepartmentId(departmentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get active employees.
     * 
     * @return list of active employees
     */
    public List<EmployeeDto> getActiveEmployees() {
        log.debug("Fetching active employees");
        return employeeRepository.findByIsActive(true).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Convert Employee entity to EmployeeDto.
     * 
     * @param employee the employee entity
     * @return the employee DTO
     */
    private EmployeeDto convertToDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .departmentId(employee.getDepartmentId())
                .position(employee.getPosition())
                .hireDate(employee.getHireDate())
                .salary(employee.getSalary())
                .isActive(employee.getIsActive())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }
}
