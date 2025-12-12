package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderDto;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderItem;
import com.ecommerce.order.exception.OrderNotFoundException;
import com.ecommerce.order.exception.OrderAlreadyExistsException;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service class for Order business logic.
 * 
 * @author E-commerce Team
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    /**
     * Create a new order.
     * 
     * @param orderDto the order data
     * @return the created order
     * @throws OrderAlreadyExistsException if an order with the same order number already exists
     */
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        log.info("Creating new order for customer: {}", orderDto.getCustomerEmail());

        if (orderRepository.existsByOrderNumber(orderDto.getOrderNumber())) {
            throw new OrderAlreadyExistsException("Order with number " + orderDto.getOrderNumber() + " already exists");
        }

        Order order = orderMapper.toEntity(orderDto);
        
        if (order.getOrderNumber() == null) {
            order.setOrderNumber(generateUniqueOrderNumber());
        }

        calculateOrderTotal(order);
        
        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully with ID: {}", savedOrder.getId());

        return orderMapper.toDto(savedOrder);
    }

    /**
     * Get an order by ID.
     * 
     * @param id the order ID
     * @return the order
     * @throws OrderNotFoundException if the order is not found
     */
    public OrderDto getOrderById(Long id) {
        log.debug("Fetching order with ID: {}", id);
        
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        return orderMapper.toDto(order);
    }

    /**
     * Get an order by order number.
     * 
     * @param orderNumber the order number
     * @return the order
     * @throws OrderNotFoundException if the order is not found
     */
    public OrderDto getOrderByOrderNumber(String orderNumber) {
        log.debug("Fetching order with order number: {}", orderNumber);
        
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with order number: " + orderNumber));

        return orderMapper.toDto(order);
    }

    /**
     * Get all orders.
     * 
     * @return list of all orders
     */
    public List<OrderDto> getAllOrders() {
        log.debug("Fetching all orders");
        
        List<Order> orders = orderRepository.findAll();
        return orderMapper.toDtoList(orders);
    }

    /**
     * Get orders by customer email.
     * 
     * @param customerEmail the customer email
     * @return list of orders for the customer
     */
    public List<OrderDto> getOrdersByCustomerEmail(String customerEmail) {
        log.debug("Fetching orders for customer email: {}", customerEmail);
        
        List<Order> orders = orderRepository.findByCustomerEmail(customerEmail);
        return orderMapper.toDtoList(orders);
    }

    /**
     * Get orders by status.
     * 
     * @param status the order status
     * @return list of orders with the specified status
     */
    public List<OrderDto> getOrdersByStatus(Order.OrderStatus status) {
        log.debug("Fetching orders with status: {}", status);
        
        List<Order> orders = orderRepository.findByStatus(status);
        return orderMapper.toDtoList(orders);
    }

    /**
     * Get orders by customer email and status.
     * 
     * @param customerEmail the customer email
     * @param status the order status
     * @return list of orders matching both criteria
     */
    public List<OrderDto> getOrdersByCustomerEmailAndStatus(String customerEmail, Order.OrderStatus status) {
        log.debug("Fetching orders for customer email: {} with status: {}", customerEmail, status);
        
        List<Order> orders = orderRepository.findByCustomerEmailAndStatus(customerEmail, status);
        return orderMapper.toDtoList(orders);
    }

    /**
     * Get orders created within a date range.
     * 
     * @param startDate the start date
     * @param endDate the end date
     * @return list of orders created within the date range
     */
    public List<OrderDto> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Fetching orders between {} and {}", startDate, endDate);
        
        List<Order> orders = orderRepository.findByCreatedAtBetween(startDate, endDate);
        return orderMapper.toDtoList(orders);
    }

    /**
     * Search orders by customer name.
     * 
     * @param customerName the customer name fragment to search for
     * @return list of matching orders
     */
    public List<OrderDto> searchOrdersByCustomerName(String customerName) {
        log.debug("Searching orders by customer name: {}", customerName);
        
        List<Order> orders = orderRepository.findByCustomerNameContainingIgnoreCase(customerName);
        return orderMapper.toDtoList(orders);
    }

    /**
     * Update an existing order.
     * 
     * @param id the order ID
     * @param orderDto the updated order data
     * @return the updated order
     * @throws OrderNotFoundException if the order is not found
     * @throws OrderAlreadyExistsException if an order with the same order number already exists
     */
    @Transactional
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        log.info("Updating order with ID: {}", id);

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        if (orderDto.getOrderNumber() != null && !orderDto.getOrderNumber().equals(existingOrder.getOrderNumber())) {
            if (orderRepository.existsByOrderNumber(orderDto.getOrderNumber())) {
                throw new OrderAlreadyExistsException("Order with number " + orderDto.getOrderNumber() + " already exists");
            }
        }

        orderMapper.updateEntity(existingOrder, orderDto);
        calculateOrderTotal(existingOrder);
        
        Order updatedOrder = orderRepository.save(existingOrder);
        
        log.info("Order updated successfully with ID: {}", updatedOrder.getId());
        return orderMapper.toDto(updatedOrder);
    }

    /**
     * Update order status.
     * 
     * @param id the order ID
     * @param status the new status
     * @return the updated order
     * @throws OrderNotFoundException if the order is not found
     */
    @Transactional
    public OrderDto updateOrderStatus(Long id, Order.OrderStatus status) {
        log.info("Updating status for order with ID: {} to {}", id, status);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        
        log.info("Order status updated successfully for ID: {}", id);
        return orderMapper.toDto(updatedOrder);
    }

    /**
     * Delete an order by ID.
     * 
     * @param id the order ID
     * @throws OrderNotFoundException if the order is not found
     */
    @Transactional
    public void deleteOrder(Long id) {
        log.info("Deleting order with ID: {}", id);

        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found with ID: " + id);
        }

        orderRepository.deleteById(id);
        log.info("Order deleted successfully with ID: {}", id);
    }

    /**
     * Get order statistics.
     * 
     * @return order statistics
     */
    public OrderStatistics getOrderStatistics() {
        log.debug("Fetching order statistics");
        
        long totalOrders = orderRepository.count();
        long pendingOrders = orderRepository.countByStatus(Order.OrderStatus.PENDING);
        long confirmedOrders = orderRepository.countByStatus(Order.OrderStatus.CONFIRMED);
        long shippedOrders = orderRepository.countByStatus(Order.OrderStatus.SHIPPED);
        long deliveredOrders = orderRepository.countByStatus(Order.OrderStatus.DELIVERED);
        long cancelledOrders = orderRepository.countByStatus(Order.OrderStatus.CANCELLED);

        return new OrderStatistics(totalOrders, pendingOrders, confirmedOrders, 
                                shippedOrders, deliveredOrders, cancelledOrders);
    }

    /**
     * Calculate the total amount for an order based on its items.
     * 
     * @param order the order to calculate total for
     */
    private void calculateOrderTotal(Order order) {
        if (order.getOrderItems() != null) {
            BigDecimal total = order.getOrderItems().stream()
                    .map(OrderItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            order.setTotalAmount(total);
        }
    }

    /**
     * Generate a unique order number.
     * 
     * @return a unique order number string
     */
    private String generateUniqueOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * DTO for order statistics.
     */
    public record OrderStatistics(long totalOrders, long pendingOrders, long confirmedOrders, 
                                 long shippedOrders, long deliveredOrders, long cancelledOrders) {
    }
}
