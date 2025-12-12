package com.ecommerce.product.exception;

/**
 * Exception thrown when attempting to create a product that already exists.
 * 
 * @author E-commerce Team
 * @version 1.0.0
 */
public class ProductAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new ProductAlreadyExistsException with the specified detail message.
     * 
     * @param message the detail message
     */
    public ProductAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new ProductAlreadyExistsException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public ProductAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
