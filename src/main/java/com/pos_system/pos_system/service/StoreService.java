/**
 * WHAT THIS CLASS DOES
 *  CRUD operations for Store (create a new store, get all stores, get one store by ID, update store info, delete store).
 *  Also contains a "findByName" method for alternative searching
 */
package com.pos_system.pos_system.service;

import com.pos_system.pos_system.model.Store;
import com.pos_system.pos_system.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    /**
     * Get all stores.
     */
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    /**
     * Get a single store by ID.
     */
    public Optional<Store> getStoreById(Long id) {
        return storeRepository.findById(id);
    }

    /**
     * Check if a store exists
     */
    public boolean existsById(Long id) {
        return storeRepository.existsById(id);
    }

    /**
     * Create a new store.
     */
    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

    /**
     * Update an existing store's properties.
     */
    @Transactional
    public Store updateStore(Long id, Store updatedStore) {
        Store existing = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Store not found with id: " + id));

        existing.setName(updatedStore.getName());
        existing.setLocation(updatedStore.getLocation());

        // Persist changes and return the managed entity
        return storeRepository.save(existing);
    }

    /**
     * Delete a store by ID.
     */
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }
}
