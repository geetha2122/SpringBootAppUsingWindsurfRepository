package com.ecommerce.product.repository;

import com.ecommerce.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Product entity operations.
 * 
 * @author E-commerce Team
 * @version 1.0.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find a product by its SKU.
     * 
     * @param sku the SKU to search for
     * @return Optional containing the product if found
     */
    Optional<Product> findBySku(String sku);

    /**
     * Find products by category.
     * 
     * @param category the category to filter by
     * @return list of products in the specified category
     */
    List<Product> findByCategory(String category);

    /**
     * Find products by name containing the given string (case-insensitive).
     * 
     * @param name the name fragment to search for
     * @return list of products matching the name criteria
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * Find products with quantity greater than zero (in stock).
     * 
     * @return list of products that are in stock
     */
    @Query("SELECT p FROM Product p WHERE p.quantity > 0")
    List<Product> findInStockProducts();

    /**
     * Find products within a price range.
     * 
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return list of products within the price range
     */
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceBetween(@Param("minPrice") java.math.BigDecimal minPrice, 
                                   @Param("maxPrice") java.math.BigDecimal maxPrice);

    /**
     * Check if a product exists with the given SKU.
     * 
     * @param sku the SKU to check
     * @return true if a product with the SKU exists, false otherwise
     */
    boolean existsBySku(String sku);
}
