package com.ecommerce.product.mapper;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between Product and ProductDto objects.
 * 
 * @author E-commerce Team
 * @version 1.0.0
 */
@Component
public class ProductMapper {

    /**
     * Convert Product entity to ProductDto.
     * 
     * @param product the product entity
     * @return the product DTO
     */
    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .category(product.getCategory())
                .sku(product.getSku())
                .build();
    }

    /**
     * Convert ProductDto to Product entity.
     * 
     * @param productDto the product DTO
     * @return the product entity
     */
    public Product toEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .category(productDto.getCategory())
                .sku(productDto.getSku())
                .build();
    }

    /**
     * Convert list of Product entities to list of ProductDto objects.
     * 
     * @param products the list of product entities
     * @return the list of product DTOs
     */
    public List<ProductDto> toDtoList(List<Product> products) {
        if (products == null) {
            return null;
        }

        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Convert list of ProductDto objects to list of Product entities.
     * 
     * @param productDtos the list of product DTOs
     * @return the list of product entities
     */
    public List<Product> toEntityList(List<ProductDto> productDtos) {
        if (productDtos == null) {
            return null;
        }

        return productDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * Update Product entity with data from ProductDto.
     * 
     * @param product the existing product entity
     * @param productDto the product DTO with updated data
     */
    public void updateEntity(Product product, ProductDto productDto) {
        if (product == null || productDto == null) {
            return;
        }

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setCategory(productDto.getCategory());
        product.setSku(productDto.getSku());
    }
}
