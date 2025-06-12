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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    /**
     * Test that getAllProducts() simply returns whatever the repository.findAll() returns.
     */
    @Test
    void testGetAllProducts() {
        // Prepare two dummy Product objects with distinct IDs and descriptions
        Product p1 = new Product(1,1,1,1, new BigDecimal("19.99"),
                new BigDecimal("5.99"), "desc1", "desc2", LocalDateTime.now());
        p1.setId(11L);  // Simulate that the database assigned ID = 11

        Product p2 = new Product(1,1,1,1, new BigDecimal("19.99"),
                new BigDecimal("5.99"), "descA", "descB", LocalDateTime.now());
        p2.setId(22L);  // Simulate database ID = 22

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
}
