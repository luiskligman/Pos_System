package com.pos_system.pos_system.dto;

/**
 * DTO for creating a new Inventory record.
 */
public class CreateInventoryRequest {
    private Long storeId;
    private Long productId;
    private int initialQuantity;

    public CreateInventoryRequest() {}

    public CreateInventoryRequest(Long storeId, Long productId, int initialQuantity) {
        this.storeId = storeId;
        this.productId = productId;
        this.initialQuantity = initialQuantity;
    }

    public Long getStoreId() { return storeId; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public int getInitialQuantity() { return initialQuantity; }
    public void setInitialQuantity(int initialQuantity) { this.initialQuantity = initialQuantity; }

}
