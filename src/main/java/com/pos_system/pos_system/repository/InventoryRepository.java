package com.pos_system.pos_system.repository;

import com.pos_system.pos_system.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Find the single Inventory record for a given storeId and productId
    Optional<Inventory> findByStoreIdAndProductId(Long storeId, Long productId);

    // Find all Inventory rows for one store ( to list everything on-hand in that store )
    List<Inventory> findByStoreId(Long storeId);
}