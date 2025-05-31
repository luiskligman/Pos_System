package com.pos_system.pos_system.repository;

import com.pos_system.pos_system.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
