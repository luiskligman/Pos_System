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
@RestController  // Marks this class as a controller that returns JSON
@RequestMapping("/api/inventory")  // Base path for all inventory-related endpoints
public class InventoryController {

    // Spring will automatically inject an instance of InventoryRepository here
    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * GET /api/inventory
     * Fetches all inventory records.
     */
    @GetMapping
    public List<Inventory> getAllInventory() {
        // Calls the repository to return all Inventory objects as a last
        return inventoryRepository.findAll();
    }

    /**
     * GET /api/inventory/{id}
     * Retrieve a single inventory entry by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        //Tries to find the Inventory by its ID
        Optional<Inventory> inv = inventoryRepository.findById(id);

        // If found, return 200 OK with the inventory; else return 404 Not Found
        return inv.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/inventory
     * Create a new inventory record (associating a product with a store).
     */
    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        // Saves and returns the new inventory record
        return inventoryRepository.save(inventory);
    }

    /**
     * PUT /api/inventory/{id}
     * Update an existing inventory record
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id,
                                                     @RequestBody Inventory updated) {
        // Check if the inventory with the given ID exists
        return inventoryRepository.findById(id)
                .map(inv -> {
                    // If found, update the fields and save
                    inv.setProduct(updated.getProduct());
                    inv.setStore(updated.getStore());
                    return ResponseEntity.ok(inventoryRepository.save(inv));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());  // 404 if not found
    }

    /**
     * DELETE /api/inventory/{id}
     * Delete an inventory record by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        // If the record doesn't exist, return 404
        if (!inventoryRepository.existsById(id))
            return ResponseEntity.notFound().build();
        // Otherwise, delete it and return 204 No Content
        inventoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
