package com.pos_system.pos_system.controller;

// Standard imports for REST controllers
import com.pos_system.pos_system.model.Product;
import com.pos_system.pos_system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller to handle HTTP requests related to Products.
 * Exposes endpoints for CRUD operations on Product entities
 */
@RestController
@RequestMapping("/api/products")  // Base path for all product-related endpoints
public class ProductController {

    // Inject the repository to access Product data
    @Autowired
    private ProductService productService;

    /**
     * GET /api/products
     * @return list of all products stored in the database
     */
    @GetMapping
    public List<Product> getAllProducts() {
        // Delegate to JPA repository to fetch all product records
        return productService.getAllProducts();
    }

    /**
     * GET /api/products/{id}
     * @param id the unique identifier of the product
     * @return 200 OK with product data, or 404 Not Found if missing
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        // Attempt to find product by ID
        Optional<Product> product = productService.getProductById(id);
        // If found, wrap in ResponseEntity.ok(), else 404
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/products
     * @param product JSON body representing a new product
     * @return the saved product with generated ID
     */
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        // Save the new product; Spring Data JPA generates the INSERT SQL
        return productService.saveProduct(product);
    }

    /**
     * PUT /api/products/{id}
     * Updates an exisiting product record.
     *
     * @param id      the ID of the product to update
     * @param updated the product data to apply
     * @return 200 OK with updated data, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updated) {
        // Find existing product by ID
        try {
            Product result = productService.updateProduct(id, updated);
            return ResponseEntity.ok(result);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();

        }

//        return productService.getProductById(id)
//                .map(prod -> {
//                    // Copy over fields from the incoming object
//                    prod.setUpc(updated.getUpc());
//                    prod.setAlu(updated.getAlu());
//                    prod.setPrice(updated.getPrice());
//                    prod.setCost(updated.getCost());
//                    prod.setDesc1(updated.getDesc1());
//                    prod.setDesc2(updated.getDesc2());
//                    prod.setStoreOhQuantity(updated.getStoreOhQuantity());
//                    prod.setVendorCode(updated.getVendorCode());
//                    prod.setDateLastSold(updated.getDateLastSold());
//                    // Save and return the updated entity
//                    return ResponseEntity.ok(productRepository.save(prod));
//                })
//                // If not found, return 404
//                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/products/{id}
     * @param id remove a product by ID
     * @return 204 No Content on success, or 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
//        productService.deleteProduct(id);
//        return ResponseEntity.noContent().build();
    }
}
