package com.pos_system.pos_system.repository;

import com.pos_system.pos_system.model.Product;
import com.pos_system.pos_system.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository repository;
    
    @Test
    void findById_returnsSavedProduct() {
        Product p = new Product();
        p.setDesc1("testProduct");
        p.setAlu(11);
        p.setCost(BigDecimal.valueOf(9.99));
        Product saved = repository.save(p);
        
        Optional<Product> found = repository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getDesc1().equals("testProduct"));
    }
}
