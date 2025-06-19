package com.pos_system.pos_system.controller;

import com.pos_system.pos_system.model.Store;
import com.pos_system.pos_system.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller to manage Store entities.
 * Provides CRUD operations on stores.
 */
@RestController  // Marks this class as a REST controller that returns JSON
@RequestMapping("/api/stores")  // All endpoints will start with /api/stores
public class StoreController {

    // Spring will automatically inject the implementation of StoreRepository here
    @Autowired
    private StoreService storeService;

    /**
     * GET /api/stores
     * Retrieves all stores.
     */
    @GetMapping
    public List<Store> getAllStores() {
        // Returns all store records as a list
        return storeService.getAllStores();
    }

    /**
     * GET /api/store/{id}
     * Retrieve a single store entry by ID
     * If not found, returns 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {

        Optional<Store> str = storeService.getStoreById(id);  // Attempt to find store

        return str.map(ResponseEntity::ok) // If found, return 200 OK with the store
                .orElseGet(() -> ResponseEntity.notFound().build());  // Else 404 Not Found
    }

    /**
     * POST /api/stores
     * Creates a new store from the JSON body.
     */
    @PostMapping
    public Store createStore(@RequestBody Store store) {
        // Saves and returns the new store
        return storeService.createStore(store);
    }

    /**
     * Put /api/stores/{id}
     * Update an existing store's details.
     * If the store exists, it's updated and returned.
     * If not, returns 404 Not Found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable Long id,
                                             @RequestBody Store updated) {
        return storeService.getStoreById(id)
                .map(st -> {
                    // Update the store fields with new values
                    st.setName(updated.getName());
                    st.setLocation(updated.getLocation());
                    return ResponseEntity.ok(storeService.updateStore(id, st));  // Save and return updated store
                })
                .orElseGet(() -> ResponseEntity.notFound().build());  // 404 if store not found
    }

    /**
     * DELETE /api/stores/{id}
     * Remove a store by ID.
     * If it doesn't exist, return 404 Not Found.
     * If it exists, delete and return 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        if (!storeService.existsById(id)) {
            return ResponseEntity.notFound().build();  // 404 if not found
        }
        storeService.deleteStore(id);  // Delete the store
        return ResponseEntity.noContent().build();  // 204 No Content ( successful )
    }

}
