package com.pos_system.pos_system.dto;

/**
 * Data Transfer Object for Inventory entities.
 */
public class InventoryDto {
    private Long id;
    private Long storeId;
    private Long productId;
    private Integer quantityOnHand;

    public InventoryDto() {}

    public InventoryDto(Long id, Long storeId, long ProductId, Integer quantityOnHand) {
        this.id = id;
        this.storeId = storeId;
        this.productId = productId;
        this.quantityOnHand = quantityOnHand;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStoreId() { return storeId; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantityOnHand() { return quantityOnHand; }
    public void setQuantityOnHand(Integer quantityOnHand) { this.quantityOnHand = quantityOnHand; }
}
