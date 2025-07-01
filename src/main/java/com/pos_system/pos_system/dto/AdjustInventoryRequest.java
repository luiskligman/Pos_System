package com.pos_system.pos_system.dto;

/**
 * DTO for adjusting the quantity on hand.
 */
public class AdjustInventoryRequest {
    private Long storeId;
    private Long productId;
    private int delta;

    public AdjustInventoryRequest() {}

    public AdjustInventoryRequest(Long storeId, Long productId, int delta) {
        this.storeId = storeId;
        this.productId = productId;
        this.delta = delta;
    }

    public Long getStoreId() { return storeId; }
    public void setStoreId(Long storeId) { this.storeId = storeId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public int getDelta() { return delta; }
    public void setDelta(int delta) { this.delta = delta; }
}
