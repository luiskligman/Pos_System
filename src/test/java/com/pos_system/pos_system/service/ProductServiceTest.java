package com.pos_system.pos_system.service;

import com.pos_system.pos_system.model.Product;
import com.pos_system.pos_system.repository.ProductRepository;
import com.pos_system.pos_system.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void getProductById_exisitingId_returnsProduct() {
        Product fake = new Product();
        fake.setId(1L);
        fake.setDesc1("fakeProduct");
        when(repository.findById(1L)).thenReturn(Optional.of(fake));
        
        Optional<Product> result = service.getProductById(1L);
        assertEquals("fakeProduct", result.get());
    }
}
