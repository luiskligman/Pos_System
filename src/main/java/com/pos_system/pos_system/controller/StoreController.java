package com.pos_system.pos_system.controller;

import com.pos_system.pos_system.model.Store;
import com.pos_system.pos_system.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller to manage Store entities.
 * Provides CRUD operations on stores.
 */
@RestController
@RequestMapping("/api/stores")
public class StoreController {
    // Repository for Store persistence
    @Autowired
    private StoreRepository storeRepository;

    /**
     * GET /api/stores
     * Retrieves all stores.
     */
    @GetMapping
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    /**
     * GET /api/store/{id}
     * Retrieve a single store entry by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        Optional<Store> str = storeRepository.findById(id);
        return str.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/stores
     * Creates a new store from the JSON body.
     */
    @PostMapping
    public Store createStore(@RequestBody Store store) {
        // Creates a new store from the JSON body
        return storeRepository.save(store);
    }

    /**
     * Put /api/stores/{id}
     * Update an existing store's details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable Long id,
                                             @RequestBody Store updated) {
        return storeRepository.findById(id)
                .map(st -> {
                    st.setName(updated.getName());
                    st.setLocation(updated.getLocation());
                    return ResponseEntity.ok(storeRepository.save(st));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/stores/{id}
     * Remove a store by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        if (!storeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        storeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
