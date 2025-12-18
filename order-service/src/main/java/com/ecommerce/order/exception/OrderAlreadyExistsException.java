package com.ecommerce.order.exception;

/**
 * Exception thrown when attempting to create an order that already exists.
 * 
 * @author E-commerce Team
 * @version 1.0.0
 */
public class OrderAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new OrderAlreadyExistsException with the specified detail message.
     * 
     * @param message the detail message
     */
    public OrderAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new OrderAlreadyExistsException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public OrderAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
