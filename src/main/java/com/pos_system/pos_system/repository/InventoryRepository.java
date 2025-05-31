package com.pos_system.pos_system.repository;

import com.pos_system.pos_system.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {}