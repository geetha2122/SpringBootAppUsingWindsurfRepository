package com.ecommerce.order.dto;

import com.ecommerce.order.entity.Order;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for Order operations.
 * 
 * @author E-commerce Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;

    @NotBlank(message = "Order number is required")
    private String orderNumber;

    @NotBlank(message = "Customer name is required")
    @Size(max = 100, message = "Customer name must not exceed 100 characters")
    private String customerName;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Invalid email format")
    private String customerEmail;

    @NotNull(message = "Total amount is required")
    private BigDecimal totalAmount;

    private Order.OrderStatus status;

    @Size(max = 500, message = "Shipping address must not exceed 500 characters")
    private String shippingAddress;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<OrderItemDto> orderItems;

    /**
     * Data Transfer Object for Order Item operations.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDto {

        private Long id;
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
    }
}
