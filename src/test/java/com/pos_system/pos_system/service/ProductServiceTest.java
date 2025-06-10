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

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

//    @Test
//    void testGetProductById() {
//        Product product = new Product(1, 1, 1, 1,
//                                    new BigDecimal("19.99"), new BigDecimal("5.99"),
//                                    "test1", "test2", LocalDateTime.now());
//
//        Product savedProduct = new Product(1, 1, 1, 1,
//                new BigDecimal("19.99"), new BigDecimal("5.99"),
//                "test1", "test2", LocalDateTime.now());
//
//        product.setId(1L);
//        savedProduct.setId(2L);  // Simulate database ID
//
//        when(repository.findAll()).thenReturn(Arrays.asList(product, savedProduct));
////        when(repository.save(product)).thenReturn(savedProduct);
//
//        Optional<Product> result = service.getProductById(1L);
//        assertNotNull(result);
//        assertEquals(1L, result.get().getId());
//        assertEquals("test1", result.get().getDesc1());
//
//
//    }

    @Test
    void testGetAllProducts() {
        // prepare two dummy products
        Product p1 = new Product(1,1,1,1, new BigDecimal("19.99"),
                new BigDecimal("5.99"), "desc1", "desc2", LocalDateTime.now());
        p1.setId(11L);

        Product p2 = new Product(1,1,1,1, new BigDecimal("19.99"),
                new BigDecimal("5.99"), "descA", "descB", LocalDateTime.now());
        p2.setId(22L);

        // stub the repository
        when(repository.findAll()).thenReturn(Arrays.asList(p1, p2));

        // exercise the service
        List<Product> results = service.getAllProducts();

        // verify
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(11L, results.get(0).getId());
        assertEquals("descB", results.get(1).getDesc2());
    }
}
