package com.pos_system.pos_system.service;

import com.pos_system.pos_system.model.Product;
import com.pos_system.pos_system.repository.ProductRepository;
import com.pos_system.pos_system.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit teats for ProductService.
 * Uses Mockito to mock out the ProductRepository so we can isolate the service logic.
 */
@ExtendWith(MockitoExtension.class)  // Integrates Mockito with JUnit 5
public class ProductServiceTest {
    @Mock
    // This creates a Mockito mock of ProductRepository, so no real database is involved.
    private ProductRepository repository;

    @InjectMocks
    // Tells Mockito to inject the mock repository into this ProductService instance.
    private ProductService service;

    private Product p1;
    private Product p2;

    @BeforeEach
    void setUpProducts() {
        // Prepare two dummy Product objects with distinct IDs and descriptions
        p1 = new Product(1,1,1,1, new BigDecimal("19.99"),
                new BigDecimal("5.99"), "desc1", "desc2", LocalDateTime.now());
        p1.setId(11L);  // Simulate that the database assigned ID = 11

        p2 = new Product(1,1,1,1, new BigDecimal("19.99"),
                new BigDecimal("5.99"), "descA", "descB", LocalDateTime.now());
        p2.setId(22L);  // Simulate database ID = 22
    }

    /**
     * Create and save a product
     */
    @Test
    void testCreateProduct() {
        // Stub repository.save(...) to return p1 with ID already set
        when(repository.save(p1)).thenReturn(p1);

        // Call the service create method
        Product created = service.saveProduct(p1);

        // Verify the returned object matches and that save() was invoked
        assertNotNull(created, "Created product should not be null");
        assertEquals(11L, created.getId(), "ID should be assigned by repository");
        assertEquals("desc1", created.getDesc1(), "Desc1 should be preserved");
        verify(repository).save(p1);
    }

//    /**
//     * Test the ability to update pre-exisiting products
//     */
//    @Test
//    void testUpdateProduct() {
//        // repository.findById returns existing p1
//        when(repository.findById(11L)).thenReturn(Optional.of(p1));
//
//        // Create a DTO pr Product carrying just the new values you want to merge
//        Product updateData = new Product();
//        updateData.setDesc1("newDesc1");
//        updateData.setDesc2("newDesc2");
//
//        // Stub save(p1) to return p1 ( same instance)
//        when(repository.save(p1)).thenReturn(p1);
//
//        // Call update
//        Optional<Product> result = Optional.ofNullable(service.updateProduct(11L, updateData));
//
//        assertTrue(result.isPresent());
//
//
//    }


    /**
     * Test that getAllProducts() simply returns whatever the repository.findAll() returns.
     */
    @Test
    void testGetAllProducts() {
        // Stub the repository so that findAll() returns our two dummy products
        when(repository.findAll()).thenReturn(Arrays.asList(p1, p2));

        // Call the service method under test
        List<Product> results = service.getAllProducts();

        // Verify that the elements of the dummy objects match the values we intended to set them to.
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(11L, results.get(0).getId());
        assertEquals("descB", results.get(1).getDesc2());
    }

    /**
     * Test that getProductById() simply returns whatever the repository.findById() returns.
     */
    @Test
    void testGetProductById() {
        // Stub the repository to return p1 when ID = 11
        when(repository.findById(11L)).thenReturn(Optional.of(p1));

        // Call the service
        Optional<Product> result = service.getProductById(11L);

        // The Optional is present and contains the expected product
        assertNotNull(result);
        assertEquals(11L, result.get().getId());
        assertEquals("desc2", result.get().getDesc2());
    }
}
