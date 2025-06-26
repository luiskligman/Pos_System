/**
 * WHAT THIS CLASS DOES
 *  Exposes simple CRUD operations for Product (save, find, update, delete).
 *  Keeps all "product-specific" database access in one place, so the controllers never call ProductRepository directly
 *  As I add more rules later (e.g. "no two products can share the same UPC"), i'd modify only this class
 */

package com.pos_system.pos_system.service;

import com.pos_system.pos_system.model.Product;
import com.pos_system.pos_system.repository.ProductRepository;
import com.pos_system.pos_system.dto.ProductUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    // Constructor injection ensures Spring will provide the correct bean
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Fetch all products in the database.
     * Returns an empty list if none exist.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Fetch a single product by its ID.
     * Returns Optional.empty() if no product is found.
     */
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Create a new product (or update if the product.id is already set).
     * Because this is a straighforward save, no transaction annotation is necessary here,
     * but Spring Data JPA will run it in a transaction anyway.
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Update an existing product.
     * First, verify that a product with this ID actually exists.
     * If it does, copy over fields from the incoming DTO and save.
     * Throw an exception ( IllegalArgumentException ) if not found.
     */
    @Transactional
    public Product updateProduct(long id, ProductUpdateDto dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

        if (dto.getAlu() != null)
            existing.setAlu(dto.getAlu());
        if (dto.getUpc() != null)
            existing.setUpc(dto.getUpc());
        if (dto.getVendorCode() != null)
            existing.setVendorCode(dto.getVendorCode());
        if (dto.getStoreOhQuantity() != null)
            existing.setStoreOhQuantity(dto.getStoreOhQuantity());
        if (dto.getPrice() != null)
            existing.setPrice(dto.getPrice());
        if (dto.getCost() != null)
            existing.setCost(dto.getCost());
        if (dto.getDesc1() != null)
            existing.setDesc1(dto.getDesc1());
        if (dto.getDesc2() != null)
            existing.setDesc2(dto.getDesc2());
        if (dto.getDateLastSold() != null)
            existing.setDateLastSold(dto.getDateLastSold());

        // Persist changes and return the managed entity
        return productRepository.save(existing);
    }

    /**
     * Delete a product by its ID. If nothing exists with that ID,
     * JPARepository.deleteById will throw EmptyResultDataAccessException.
     */
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

}
