package com.pos_system.pos_system.service;


import com.pos_system.pos_system.model.Store;
import com.pos_system.pos_system.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)  // Integrates Mockito with JUnit 5
public class StoreServiceTest {
    @Mock
    // This creates a Mockito mock of StoreRepository, so no real database is involved.
    private StoreRepository repository;

    @InjectMocks
    // Tells Mockito to inject the mock repository into this StoreService instance.
    private StoreService service;

    private Store s1;
    private Store s2;

    @BeforeEach
    void setUpStores() {
        // Prepare two dummy Store objects with distinct IDs and descriptions
        s1 = new Store("store1", "123 A Street");
        s1.setId(12L);

        s2 = new Store("store2", "456 B Street");
        s2.setId(22L);
    }

    /**
     * Create and save a Store.
     */
    @Test
    void testCreateStore() {
        // Stub repository.save(...) to return s1 with ID already set
        when(repository.save(s1)).thenReturn(s1);

        // Call the service create method
        Store created = service.createStore(s1);

        // Verify the returned object matches and that save() was invoked
        assertNotNull(created, "Created store should not be null");
        assertEquals(12L, created.getId());
        assertEquals("store1", created.getName());
        assertEquals("123 A Street", created.getLocation());
        verify(repository).save(s1);
    }

    /**
     * Test that getAllStores() simply returns whatever the repository.findAll() returns.
     */
    @Test
    void testGetAllStores() {
        // Stub the repository so that findAll() returns our two dummy stores
        when(repository.findAll()).thenReturn(Arrays.asList(s1, s2));

        // Call the service method under test
        List<Store> results = service.getAllStores();

        // Verify that the elements of the dummy objects match the values we intended to set them to.
        assertNotNull(results, "Results should not be null");
        assertEquals(2, results.size(), "Results size should be 2");
        assertEquals(12L, results.get(0).getId(), "Id should be assigned by repository");
        assertEquals("store2", results.get(1).getName(), "Name should be preserved");
    }

    @Test
    void testUppdateStore() {
        // repository.findById returns existing s1
        when(repository.findById(12L)).thenReturn(Optional.of(s1));

        // Create a DTO Store carrying just the new values you want to merge
        Store updateStore = new Store();
        updateStore.setName("1store");
        updateStore.setLocation("1st street");

        // Stub save(s1) to return s1 ( same instance )
        when(repository.save(s1)).thenReturn(s1);

        // Act
        Store result = service.updateStore(12L, updateStore);

//        // Call update
//        Optional<Store> result = Optional.ofNullable(service.updateStore(12L, updateStore));

        assertNotNull(result, "Result should not be null");
        assertEquals(12L, result.getId(), "ID should be assigned by repository");
        assertEquals("1st street", result.getLocation(), "Name should be preserved");
//        verify(repository).save(s1);
    }
}
