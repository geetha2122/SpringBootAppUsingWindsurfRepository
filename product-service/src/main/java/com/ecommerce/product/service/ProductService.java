package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.exception.ProductAlreadyExistsException;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Service class for Product business logic.
 * 
 * @author E-commerce Team
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * Create a new product.
     * 
     * @param productDto the product data
     * @return the created product
     * @throws ProductAlreadyExistsException if a product with the same SKU already exists
     */
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Creating new product: {}", productDto.getName());

        if (productDto.getSku() != null && productRepository.existsBySku(productDto.getSku())) {
            throw new ProductAlreadyExistsException("Product with SKU " + productDto.getSku() + " already exists");
        }

        Product product = productMapper.toEntity(productDto);
        
        if (product.getSku() == null) {
            product.setSku(generateUniqueSku());
        }

        Product savedProduct = productRepository.save(product);
        log.info("Product created successfully with ID: {}", savedProduct.getId());

        return productMapper.toDto(savedProduct);
    }

    /**
     * Get a product by ID.
     * 
     * @param id the product ID
     * @return the product
     * @throws ProductNotFoundException if the product is not found
     */
    public ProductDto getProductById(Long id) {
        log.debug("Fetching product with ID: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        return productMapper.toDto(product);
    }

    /**
     * Get a product by SKU.
     * 
     * @param sku the product SKU
     * @return the product
     * @throws ProductNotFoundException if the product is not found
     */
    public ProductDto getProductBySku(String sku) {
        log.debug("Fetching product with SKU: {}", sku);
        
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with SKU: " + sku));

        return productMapper.toDto(product);
    }

    /**
     * Get all products.
     * 
     * @return list of all products
     */
    public List<ProductDto> getAllProducts() {
        log.debug("Fetching all products");
        
        List<Product> products = productRepository.findAll();
        return productMapper.toDtoList(products);
    }

    /**
     * Get products by category.
     * 
     * @param category the category to filter by
     * @return list of products in the category
     */
    public List<ProductDto> getProductsByCategory(String category) {
        log.debug("Fetching products by category: {}", category);
        
        List<Product> products = productRepository.findByCategory(category);
        return productMapper.toDtoList(products);
    }

    /**
     * Get in-stock products.
     * 
     * @return list of products with quantity > 0
     */
    public List<ProductDto> getInStockProducts() {
        log.debug("Fetching in-stock products");
        
        List<Product> products = productRepository.findInStockProducts();
        return productMapper.toDtoList(products);
    }

    /**
     * Search products by name.
     * 
     * @param name the name fragment to search for
     * @return list of matching products
     */
    public List<ProductDto> searchProductsByName(String name) {
        log.debug("Searching products by name: {}", name);
        
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return productMapper.toDtoList(products);
    }

    /**
     * Get products within a price range.
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return list of products within the price range
     */
    public List<ProductDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        log.debug("Fetching products within price range: {} - {}", minPrice, maxPrice);
        
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        return productMapper.toDtoList(products);
    }

    /**
     * Update an existing product.
     * 
     * @param id the product ID
     * @param productDto the updated product data
     * @return the updated product
     * @throws ProductNotFoundException if the product is not found
     * @throws ProductAlreadyExistsException if a product with the same SKU already exists
     */
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        log.info("Updating product with ID: {}", id);

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        if (productDto.getSku() != null && !productDto.getSku().equals(existingProduct.getSku())) {
            if (productRepository.existsBySku(productDto.getSku())) {
                throw new ProductAlreadyExistsException("Product with SKU " + productDto.getSku() + " already exists");
            }
        }

        productMapper.updateEntity(existingProduct, productDto);
        Product updatedProduct = productRepository.save(existingProduct);
        
        log.info("Product updated successfully with ID: {}", updatedProduct.getId());
        return productMapper.toDto(updatedProduct);
    }

    /**
     * Delete a product by ID.
     * 
     * @param id the product ID
     * @throws ProductNotFoundException if the product is not found
     */
    @Transactional
    public void deleteProduct(Long id) {
        log.info("Deleting product with ID: {}", id);

        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }

        productRepository.deleteById(id);
        log.info("Product deleted successfully with ID: {}", id);
    }

    /**
     * Update product quantity.
     * 
     * @param id the product ID
     * @param quantity the new quantity
     * @return the updated product
     * @throws ProductNotFoundException if the product is not found
     */
    @Transactional
    public ProductDto updateProductQuantity(Long id, Integer quantity) {
        log.info("Updating quantity for product with ID: {} to {}", id, quantity);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        product.setQuantity(quantity);
        Product updatedProduct = productRepository.save(product);
        
        log.info("Product quantity updated successfully for ID: {}", id);
        return productMapper.toDto(updatedProduct);
    }

    /**
     * Generate a unique SKU.
     * 
     * @return a unique SKU string
     */
    private String generateUniqueSku() {
        return "SKU-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
