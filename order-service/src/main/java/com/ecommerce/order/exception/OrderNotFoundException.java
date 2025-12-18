package com.ecommerce.order.exception;

/**
 * Exception thrown when an order is not found.
 * 
 * @author E-commerce Team
 * @version 1.0.0
 */
public class OrderNotFoundException extends RuntimeException {

    /**
     * Constructs a new OrderNotFoundException with the specified detail message.
     * 
     * @param message the detail message
     */
    public OrderNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new OrderNotFoundException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
