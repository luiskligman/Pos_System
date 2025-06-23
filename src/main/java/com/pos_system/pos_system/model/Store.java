package com.pos_system.pos_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * JPA entity representing a Store in the system.
 * We add validation annotations to enforce constraints on incoming data.
 */
@Entity
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name must not be null or empty, and must satisfy length constraints
    @NotBlank(message = "Store name is required")
    @Size(max = 100, message = "Store name must be at most 100 characters")
    @Column(nullable = false, unique = true)
    private String name;

    // Location could be NULL or empty as it could pertain to an online store
    private String location;

    // Prevent infinite recursion in JSON serialization by ignoring this side
    @JsonIgnore
    // One store has many inventory items
    @OneToMany(mappedBy = "store")
    private List<Inventory> inventoryItems;

    // Default Constructor
    public Store() {}

    // Parameterized Constructor
    public Store(String name, String location) {
        this.name = name;
        this.location = location;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }

    // Setters
    public void setId(Long id) {this.id = id; };
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }

    // List
    public List<Inventory> getInventoryItems() { return inventoryItems;}

    public void setInventoryItems(List<Inventory> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

}
