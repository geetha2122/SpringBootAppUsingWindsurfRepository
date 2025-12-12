package com.ecommerce.order.mapper;

import com.ecommerce.order.dto.OrderDto;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between Order and OrderDto objects.
 * 
 * @author E-commerce Team
 * @version 1.0.0
 */
@Component
public class OrderMapper {

    /**
     * Convert Order entity to OrderDto.
     * 
     * @param order the order entity
     * @return the order DTO
     */
    public OrderDto toDto(Order order) {
        if (order == null) {
            return null;
        }

        List<OrderDto.OrderItemDto> orderItemDtos = null;
        if (order.getOrderItems() != null) {
            orderItemDtos = order.getOrderItems().stream()
                    .map(this::toOrderItemDto)
                    .collect(Collectors.toList());
        }

        return OrderDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerName(order.getCustomerName())
                .customerEmail(order.getCustomerEmail())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .shippingAddress(order.getShippingAddress())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .orderItems(orderItemDtos)
                .build();
    }

    /**
     * Convert OrderDto to Order entity.
     * 
     * @param orderDto the order DTO
     * @return the order entity
     */
    public Order toEntity(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }

        Order order = Order.builder()
                .id(orderDto.getId())
                .orderNumber(orderDto.getOrderNumber())
                .customerName(orderDto.getCustomerName())
                .customerEmail(orderDto.getCustomerEmail())
                .totalAmount(orderDto.getTotalAmount())
                .status(orderDto.getStatus())
                .shippingAddress(orderDto.getShippingAddress())
                .build();

        if (orderDto.getOrderItems() != null) {
            List<OrderItem> orderItems = orderDto.getOrderItems().stream()
                    .map(this::toOrderItemEntity)
                    .collect(Collectors.toList());
            order.setOrderItems(orderItems);
        }

        return order;
    }

    /**
     * Convert OrderItem entity to OrderItemDto.
     * 
     * @param orderItem the order item entity
     * @return the order item DTO
     */
    private OrderDto.OrderItemDto toOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        return OrderDto.OrderItemDto.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProductId())
                .productName(orderItem.getProductName())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }

    /**
     * Convert OrderItemDto to OrderItem entity.
     * 
     * @param orderItemDto the order item DTO
     * @return the order item entity
     */
    private OrderItem toOrderItemEntity(OrderDto.OrderItemDto orderItemDto) {
        if (orderItemDto == null) {
            return null;
        }

        return OrderItem.builder()
                .id(orderItemDto.getId())
                .productId(orderItemDto.getProductId())
                .productName(orderItemDto.getProductName())
                .quantity(orderItemDto.getQuantity())
                .unitPrice(orderItemDto.getUnitPrice())
                .totalPrice(orderItemDto.getTotalPrice())
                .build();
    }

    /**
     * Convert list of Order entities to list of OrderDto objects.
     * 
     * @param orders the list of order entities
     * @return the list of order DTOs
     */
    public List<OrderDto> toDtoList(List<Order> orders) {
        if (orders == null) {
            return null;
        }

        return orders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Update Order entity with data from OrderDto.
     * 
     * @param order the existing order entity
     * @param orderDto the order DTO with updated data
     */
    public void updateEntity(Order order, OrderDto orderDto) {
        if (order == null || orderDto == null) {
            return;
        }

        order.setOrderNumber(orderDto.getOrderNumber());
        order.setCustomerName(orderDto.getCustomerName());
        order.setCustomerEmail(orderDto.getCustomerEmail());
        order.setTotalAmount(orderDto.getTotalAmount());
        order.setStatus(orderDto.getStatus());
        order.setShippingAddress(orderDto.getShippingAddress());
    }
}
