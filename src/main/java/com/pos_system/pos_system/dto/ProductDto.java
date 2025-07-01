package com.pos_system.pos_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for partial updates to Product entities.
 * Fields left as null will not overwrite existing values.
 */
public class ProductDto {
    private Integer alu;
    private Integer upc;
    private Integer vendorCode;
    private Integer storeOhQuantity;
    private BigDecimal price;
    private BigDecimal cost;
    private String desc1;
    private String desc2;
    private LocalDateTime dateLastSold;

    /**
     * Default constructor needed for serialization/deserialization
     */
    public ProductDto() {
    }

    /**
     * All-args constructor for manual instantiation or testing.
     */
    public ProductDto(
            Integer alu,
            Integer upc,
            Integer vendorCode,
            Integer storeOhQuantity,
            BigDecimal price,
            BigDecimal cost,
            String desc1,
            String desc2,
            LocalDateTime dateLastSold
    ) {
        this.alu = alu;
        this.upc = upc;
        this.vendorCode = vendorCode;
        this.storeOhQuantity = storeOhQuantity;
        this.price = price;
        this.cost = cost;
        this.desc1 = desc1;
        this.desc2 = desc2;
        this.dateLastSold = dateLastSold;
    }

    public Integer getAlu() {
        return alu;
    }
    public void setAlu(Integer alu) {
        this.alu = alu;
    }

    public Integer getUpc() {
        return upc;
    }
    public void setUpc(Integer upc) {
        this.upc = upc;
    }

    public Integer getVendorCode() {
        return vendorCode;
    }
    public void setVendorCode(Integer vendorCode) {
        this.vendorCode = vendorCode;
    }

    public Integer getStoreOhQuantity() {
        return storeOhQuantity;
    }
    public void setStoreOhQuantity(Integer storeOhQuantity) {
        this.storeOhQuantity = storeOhQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCost() {
        return cost;
    }
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDesc1() {
        return desc1;
    }
    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }
    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public LocalDateTime getDateLastSold() {
        return dateLastSold;
    }
    public void setDateLastSold(LocalDateTime dateLastSold) {
        this.dateLastSold = dateLastSold;
    }

}
