package com.pos_system.pos_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;

@Entity  // Marks this class as a JPA entity to be mapped to a database table
@Table(name = "inventory")  // Specifies the name of the table in the database
public class Inventory {

    @Id  // Specifies the primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many inventory records can be linked to the same product
    @ManyToOne  // many inventory records can reference the same product
    @JoinColumn(name = "product_id", nullable = false)  // Creates a foreign key column 'product_id' in the inventory table
    private Product product;  // The product this inventory entry refers to

    // Many inventory records can be linked to the same store
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // Tracks how many units of this product are in stock at this store
    @Column(name = "quantity_on_hand", nullable = false)
    private Integer quantityOnHand;

    // Default Constructor
    public Inventory() {}

    // Parameterized Constructor
    public Inventory(Product product, Store store, Integer quantityOnHand) {
        this.product = product;
        this.store = store;
        this.quantityOnHand = quantityOnHand;

    }

    // Getters
    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public Store getStore() { return store; }
    public Integer getQuantityOnHand() { return quantityOnHand; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setProduct(Product product) { this.product = product; }
    public void setStore(Store store) { this.store = store; }
    public void setQuantityOnHand(Integer quantityOnHand) { this.quantityOnHand = quantityOnHand; }

}
