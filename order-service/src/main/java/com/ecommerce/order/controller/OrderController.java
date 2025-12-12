package com.ecommerce.order.controller;

import com.ecommerce.order.dto.OrderDto;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for Order operations.
 * 
 * @author E-commerce Team
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Create a new order.
     * 
     * @param orderDto the order data
     * @return the created order
     */
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto) {
        log.info("POST /api/v1/orders - Creating new order for customer: {}", orderDto.getCustomerEmail());
        
        OrderDto createdOrder = orderService.createOrder(orderDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    /**
     * Get an order by ID.
     * 
     * @param id the order ID
     * @return the order
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        log.debug("GET /api/v1/orders/{} - Fetching order", id);
        
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    /**
     * Get an order by order number.
     * 
     * @param orderNumber the order number
     * @return the order
     */
    @GetMapping("/order-number/{orderNumber}")
    public ResponseEntity<OrderDto> getOrderByOrderNumber(@PathVariable String orderNumber) {
        log.debug("GET /api/v1/orders/order-number/{} - Fetching order by order number", orderNumber);
        
        OrderDto order = orderService.getOrderByOrderNumber(orderNumber);
        return ResponseEntity.ok(order);
    }

    /**
     * Get all orders.
     * 
     * @return list of all orders
     */
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        log.debug("GET /api/v1/orders - Fetching all orders");
        
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * Get orders by customer email.
     * 
     * @param customerEmail the customer email
     * @return list of orders for the customer
     */
    @GetMapping("/customer/{customerEmail}")
    public ResponseEntity<List<OrderDto>> getOrdersByCustomerEmail(@PathVariable String customerEmail) {
        log.debug("GET /api/v1/orders/customer/{} - Fetching orders for customer", customerEmail);
        
        List<OrderDto> orders = orderService.getOrdersByCustomerEmail(customerEmail);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get orders by status.
     * 
     * @param status the order status
     * @return list of orders with the specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDto>> getOrdersByStatus(@PathVariable Order.OrderStatus status) {
        log.debug("GET /api/v1/orders/status/{} - Fetching orders by status", status);
        
        List<OrderDto> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get orders by customer email and status.
     * 
     * @param customerEmail the customer email
     * @param status the order status
     * @return list of orders matching both criteria
     */
    @GetMapping("/customer/{customerEmail}/status/{status}")
    public ResponseEntity<List<OrderDto>> getOrdersByCustomerEmailAndStatus(
            @PathVariable String customerEmail,
            @PathVariable Order.OrderStatus status) {
        log.debug("GET /api/v1/orders/customer/{}/status/{} - Fetching orders by customer and status", 
                customerEmail, status);
        
        List<OrderDto> orders = orderService.getOrdersByCustomerEmailAndStatus(customerEmail, status);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get orders created within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of orders created within the date range
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<OrderDto>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.debug("GET /api/v1/orders/date-range?startDate={}&endDate={} - Fetching orders by date range", 
                startDate, endDate);
        
        List<OrderDto> orders = orderService.getOrdersByDateRange(startDate, endDate);
        return ResponseEntity.ok(orders);
    }

    /**
     * Search orders by customer name.
     * 
     * @param customerName the customer name fragment to search for
     * @return list of matching orders
     */
    @GetMapping("/search")
    public ResponseEntity<List<OrderDto>> searchOrdersByCustomerName(@RequestParam String customerName) {
        log.debug("GET /api/v1/orders/search?customerName={} - Searching orders by customer name", customerName);
        
        List<OrderDto> orders = orderService.searchOrdersByCustomerName(customerName);
        return ResponseEntity.ok(orders);
    }

    /**
     * Update an existing order.
     * 
     * @param id the order ID
     * @param orderDto the updated order data
     * @return the updated order
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody OrderDto orderDto) {
        log.info("PUT /api/v1/orders/{} - Updating order", id);
        
        OrderDto updatedOrder = orderService.updateOrder(id, orderDto);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Update order status.
     * 
     * @param id the order ID
     * @param status the new status
     * @return the updated order
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam Order.OrderStatus status) {
        log.info("PATCH /api/v1/orders/{}/status?status={} - Updating order status", id, status);
        
        OrderDto updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Delete an order by ID.
     * 
     * @param id the order ID
     * @return no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.info("DELETE /api/v1/orders/{} - Deleting order", id);
        
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get order statistics.
     * 
     * @return order statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<OrderService.OrderStatistics> getOrderStatistics() {
        log.debug("GET /api/v1/orders/statistics - Fetching order statistics");
        
        OrderService.OrderStatistics statistics = orderService.getOrderStatistics();
        return ResponseEntity.ok(statistics);
    }
}
