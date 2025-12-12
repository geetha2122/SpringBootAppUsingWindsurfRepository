package com.ecommerce.product.controller;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST controller for Product operations.
 * 
 * @author E-commerce Team
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Create a new product.
     * 
     * @param productDto the product data
     * @return the created product
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("POST /api/v1/products - Creating new product: {}", productDto.getName());
        
        ProductDto createdProduct = productService.createProduct(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    /**
     * Get a product by ID.
     * 
     * @param id the product ID
     * @return the product
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        log.debug("GET /api/v1/products/{} - Fetching product", id);
        
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    /**
     * Get a product by SKU.
     * 
     * @param sku the product SKU
     * @return the product
     */
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductDto> getProductBySku(@PathVariable String sku) {
        log.debug("GET /api/v1/products/sku/{} - Fetching product by SKU", sku);
        
        ProductDto product = productService.getProductBySku(sku);
        return ResponseEntity.ok(product);
    }

    /**
     * Get all products.
     * 
     * @return list of all products
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        log.debug("GET /api/v1/products - Fetching all products");
        
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Get products by category.
     * 
     * @param category the category to filter by
     * @return list of products in the category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable String category) {
        log.debug("GET /api/v1/products/category/{} - Fetching products by category", category);
        
        List<ProductDto> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    /**
     * Get in-stock products.
     * 
     * @return list of products with quantity > 0
     */
    @GetMapping("/in-stock")
    public ResponseEntity<List<ProductDto>> getInStockProducts() {
        log.debug("GET /api/v1/products/in-stock - Fetching in-stock products");
        
        List<ProductDto> products = productService.getInStockProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Search products by name.
     * 
     * @param name the name fragment to search for
     * @return list of matching products
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProductsByName(@RequestParam String name) {
        log.debug("GET /api/v1/products/search?name={} - Searching products by name", name);
        
        List<ProductDto> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    /**
     * Get products within a price range.
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return list of products within the price range
     */
    @GetMapping("/price-range")
    public ResponseEntity<List<ProductDto>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        log.debug("GET /api/v1/products/price-range?minPrice={}&maxPrice={} - Fetching products by price range", 
                minPrice, maxPrice);
        
        List<ProductDto> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    /**
     * Update an existing product.
     * 
     * @param id the product ID
     * @param productDto the updated product data
     * @return the updated product
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto productDto) {
        log.info("PUT /api/v1/products/{} - Updating product", id);
        
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Update product quantity.
     * 
     * @param id the product ID
     * @param quantity the new quantity
     * @return the updated product
     */
    @PatchMapping("/{id}/quantity")
    public ResponseEntity<ProductDto> updateProductQuantity(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        log.info("PATCH /api/v1/products/{}/quantity?quantity={} - Updating product quantity", id, quantity);
        
        ProductDto updatedProduct = productService.updateProductQuantity(id, quantity);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Delete a product by ID.
     * 
     * @param id the product ID
     * @return no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("DELETE /api/v1/products/{} - Deleting product", id);
        
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
