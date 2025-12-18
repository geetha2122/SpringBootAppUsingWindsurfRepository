package com.employee.service.repository;

import com.employee.service.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Employee entity operations.
 * 
 * @author Employee Service Team
 * @version 1.0.0
 * @since 2025-12-12
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find employee by email.
     * 
     * @param email the email address
     * @return Optional containing the employee if found
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Find employees by department ID.
     * 
     * @param departmentId the department ID
     * @return list of employees in the department
     */
    List<Employee> findByDepartmentId(Long departmentId);

    /**
     * Find employees by active status.
     * 
     * @param isActive the active status
     * @return list of active or inactive employees
     */
    List<Employee> findByIsActive(Boolean isActive);

    /**
     * Find employees by first name and last name.
     * 
     * @param firstName the first name
     * @param lastName the last name
     * @return list of employees matching the name
     */
    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Count employees by department ID.
     * 
     * @param departmentId the department ID
     * @return count of employees in the department
     */
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.departmentId = :departmentId")
    Long countByDepartmentId(@Param("departmentId") Long departmentId);

    /**
     * Find employees by position.
     * 
     * @param position the position
     * @return list of employees with the specified position
     */
    List<Employee> findByPosition(String position);
}
