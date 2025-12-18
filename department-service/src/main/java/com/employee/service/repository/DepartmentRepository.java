package com.employee.service.repository;

import com.employee.service.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Department entity operations.
 * 
 * @author Department Service Team
 * @version 1.0.0
 * @since 2025-12-12
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    /**
     * Find department by name.
     * 
     * @param name the department name
     * @return Optional containing the department if found
     */
    Optional<Department> findByName(String name);

    /**
     * Find department by code.
     * 
     * @param code the department code
     * @return Optional containing the department if found
     */
    Optional<Department> findByCode(String code);

    /**
     * Find departments by active status.
     * 
     * @param isActive the active status
     * @return list of active or inactive departments
     */
    List<Department> findByIsActive(Boolean isActive);

    /**
     * Find departments by location.
     * 
     * @param location the location
     * @return list of departments in the location
     */
    List<Department> findByLocation(String location);

    /**
     * Find departments by manager email.
     * 
     * @param managerEmail the manager email
     * @return list of departments managed by the specified manager
     */
    List<Department> findByManagerEmail(String managerEmail);

    /**
     * Count active departments.
     * 
     * @return count of active departments
     */
    @Query("SELECT COUNT(d) FROM Department d WHERE d.isActive = true")
    Long countActiveDepartments();

    /**
     * Find departments by name containing the given string (case-insensitive).
     * 
     * @param name the name fragment
     * @return list of departments matching the name fragment
     */
    @Query("SELECT d FROM Department d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Department> findByNameContainingIgnoreCase(@Param("name") String name);

    /**
     * Check if department code exists.
     * 
     * @param code the department code
     * @return true if exists, false otherwise
     */
    boolean existsByCode(String code);

    /**
     * Check if department name exists.
     * 
     * @param name the department name
     * @return true if exists, false otherwise
     */
    boolean existsByName(String name);
}
