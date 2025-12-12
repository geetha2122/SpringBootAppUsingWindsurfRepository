package com.ecommerce.order.repository;

import com.ecommerce.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Order entity operations.
 * 
 * @author E-commerce Team
 * @version 1.0.0
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find an order by its order number.
     * 
     * @param orderNumber the order number to search for
     * @return Optional containing the order if found
     */
    Optional<Order> findByOrderNumber(String orderNumber);

    /**
     * Find orders by customer email.
     * 
     * @param customerEmail the customer email to filter by
     * @return list of orders for the specified customer
     */
    List<Order> findByCustomerEmail(String customerEmail);

    /**
     * Find orders by status.
     * 
     * @param status the order status to filter by
     * @return list of orders with the specified status
     */
    List<Order> findByStatus(Order.OrderStatus status);

    /**
     * Find orders by customer email and status.
     * 
     * @param customerEmail the customer email
     * @param status the order status
     * @return list of orders matching both criteria
     */
    List<Order> findByCustomerEmailAndStatus(String customerEmail, Order.OrderStatus status);

    /**
     * Find orders created within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of orders created within the date range
     */
    @Query("SELECT o FROM Order o WHERE o.createdAt BETWEEN :startDate AND :endDate")
    List<Order> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate);

    /**
     * Find orders by customer name containing the given string (case-insensitive).
     * 
     * @param customerName the customer name fragment to search for
     * @return list of orders matching the customer name criteria
     */
    List<Order> findByCustomerNameContainingIgnoreCase(String customerName);

    /**
     * Check if an order exists with the given order number.
     * 
     * @param orderNumber the order number to check
     * @return true if an order with the order number exists, false otherwise
     */
    boolean existsByOrderNumber(String orderNumber);

    /**
     * Count orders by status.
     * 
     * @param status the order status
     * @return the count of orders with the specified status
     */
    long countByStatus(Order.OrderStatus status);
}
