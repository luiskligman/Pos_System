package com.pos_system.pos_system.controller;

import com.pos_system.pos_system.model.Inventory;
import com.pos_system.pos_system.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller to handle Inventory operations.
 * Inventory links Products and Stores
 */
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    // Repository for Inventory persistence
    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * GET /api/inventory
     * Fetches all inventory records.
     */
    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    /**
     * GET /api/inventory/{id}
     * Retrieve a single inventory entry by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        Optional<Inventory> inv = inventoryRepository.findById(id);
        return inv.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/inventory
     * Create a new inventory record (associating a product with a store).
     */
    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    /**
     * PUT /api/inventory/{id}
     * Update an existing inventory record
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id,
                                                     @RequestBody Inventory updated) {
        return inventoryRepository.findById(id)
                .map(inv -> {
                    inv.setProduct(updated.getProduct());
                    inv.setStore(updated.getStore());
                    return ResponseEntity.ok(inventoryRepository.save(inv));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/inventory/{id}
     * Delete an inventory record by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        if (!inventoryRepository.existsById(id))
            return ResponseEntity.notFound().build();
        inventoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
