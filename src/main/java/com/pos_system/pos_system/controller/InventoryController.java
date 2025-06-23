package com.pos_system.pos_system.controller;

import com.pos_system.pos_system.model.Inventory;
import com.pos_system.pos_system.service.InventoryService;
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
    private InventoryService inventoryService;

    /**
     * GET /api/inventory
     * Fetches all inventory records.
     */
    @GetMapping
    public List<Inventory> getAllInventory(Long storeId) {
        // Calls the repository to return all Inventory objects as a last
        return inventoryService.getInventoryByStoreId(storeId);
    }

    /**
     * GET /api/inventory/{id}
     * Retrieve a single inventory entry by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        //Tries to find the Inventory by its ID
        Optional<Inventory> inv = inventoryService.findInventoryById(id);

        // If found, return 200 OK with the inventory; else return 404 Not Found
        return inv.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/inventory
     * Create a new inventory record (associating a product with a store).
     */
    @PostMapping
    public Inventory createInventory(@PathVariable Long storeId, @RequestBody Inventory inventory) {
        // Saves and returns the new inventory record
        return inventoryService.createInventory(storeId, inventory.getId(), inventory.getQuantityOnHand());
    }

    /**
     * PUT /api/inventory/store/{storeId}/product/{productId}?delta=X
     * Adjust the quantity on hand by +/- delta for the given store/product
     */
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> adjustInventory(@PathVariable Long id, @RequestBody Inventory updated) {
        try {
            // Call service with storeId, ProductId and the delta
            Inventory saved  = inventoryService.adjustInventory(
                    updated.getStore().getId(),
                    updated.getProduct().getId(),
                    updated.getQuantityOnHand()
            );
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            // throw if no such inventory row or delta would go negative
            return ResponseEntity.badRequest().build();
        }
    }

//    public ResponseEntity<Inventory> adjustInventory(@PathVariable Long id,
//                                                     @RequestBody Inventory updated) {
//        // Check if the inventory with the given ID exists
//        return inventoryService.existsById(id)
//                .map(inv -> {
//                    // If found, update the fields and save
//                    inv.setProduct(updated.getProduct());
//                    inv.setStore(updated.getStore());
//                    return ResponseEntity.ok(inventoryRepository.save(inv));
//                })
//                .orElseGet(() -> ResponseEntity.notFound().build());  // 404 if not found
//    }

    /**
     * DELETE /api/inventory/{id}
     * Delete an inventory record by ID.
     */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
//        inventoryService.deleteInventory(id);
//
//        // If the record doesn't exist, return 404
//        if (!inventoryRepository.existsById(id))
//            return ResponseEntity.notFound().build();
//        // Otherwise, delete it and return 204 No Content
//        inventoryService.deleteInventory(id);
//        return ResponseEntity.noContent().build();
//    }
}
