package com.pos_system.pos_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity  // Marks this class as a JPA entity so it maps to a database table
@Table(name = "Products")  // Explicitly names the Database table as "products"
public class Product {

    // true database identifier
    @Id  // Specifies the primary key of the entity
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tells the Database to auto-generate the ID
    private long id;

    // DEFAULT VALUES:
    private static final int DEFAULT_ALU = 0;
    private static final int DEFAULT_UPC = 0;
    private static final int DEFAULT_VENDOR_CODE = 0;
    private static final int DEFAULT_STORE_OH_QUANTITY = 0;
    private static final BigDecimal DEFAULT_PRICE = BigDecimal.ZERO;
    private static final BigDecimal DEFAULT_COST = BigDecimal.ZERO;
    private static final String DEFAULT_DESC1 = "";
    private static final String DEFAULT_DESC2 = "";
    private static final LocalDateTime DEFAULT_DATE_LAST_SOLD = null;

    @Column(unique = true, nullable = false)  // Creates a unique constraint in the Database, field must be non-null
    private int alu;  // Associated Lookup Unit

    @Column(nullable = false)  // Field must be non-null
    private int upc;  // Universal Product Code

    @Column(nullable = false)  // Field must be non-null
    private int vendorCode;  // Arbitrary number user assigns to specific vendors
        // - all products made by specific vendor should share identical vendorCode

    @Column(name = "store_oh_quantity", nullable = false)  // Maps to column "store_oh_quantity" in the Database
    private int storeOhQuantity;  // Store on hand quantity

    @Column(nullable = false, precision = 10, scale = 2)  // Up to 10 digits total and 2 decimal places
    private BigDecimal price;  // Price that the company intends to sell the product for

    @Column(nullable = false, precision = 10, scale = 2)  // Up to 10 digits total and 2 decimal places
    private BigDecimal cost;  // Price the company purchases the product for; wholesale price

    @Column(length = 255)  // Limits the string length to 255 characters
    private String desc1;  // Arbitrary description field for user discretion

    @Column(length = 255)  // Limits the string length to 255 characters
    private String desc2;  // Arbitrary description field for user discretion

    private LocalDateTime dateLastSold;  // Record the date the item was last sold

    // Default Constructor
    public Product() {
        this.alu = DEFAULT_ALU;
        this.upc = DEFAULT_UPC;
        this.vendorCode = DEFAULT_VENDOR_CODE;
        this.storeOhQuantity = DEFAULT_STORE_OH_QUANTITY;
        this.price = DEFAULT_PRICE;
        this.cost = DEFAULT_COST;
        this.desc1 = DEFAULT_DESC1;
        this.desc2 = DEFAULT_DESC2;
        this.dateLastSold = DEFAULT_DATE_LAST_SOLD;
    }

    public Product(int alu, int upc, int vendorCode, int storeOhQuantity,
                   BigDecimal price, BigDecimal cost,
                   String desc1, String desc2, LocalDateTime dateLastSold) {
        setAlu(alu);
        setUpc(upc);
        setVendorCode(vendorCode);
        setStoreOhQuantity(storeOhQuantity);
        setPrice(price);
        setCost(cost);
        setDesc1(desc1);
        setDesc2(desc2);
        setDateLastSold(dateLastSold);
    }

    //TODO might need getter / setter for id

    // Getters
    public long getId() { return id; }
    public int getAlu() { return alu; }
    public int getUpc() { return upc; }
    public int getVendorCode() { return vendorCode; }
    public int getStoreOhQuantity() { return storeOhQuantity; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getCost() { return cost; }
    public String getDesc1() { return desc1; }
    public String getDesc2() { return desc2; }
    public LocalDateTime getDateLastSold() { return dateLastSold; }

    // Setters
    public void setAlu(int alu) {
        if (alu > 0)
            this.alu = alu;
        else
            this.alu = DEFAULT_ALU;
    }

    // Allows duplicates, UPC ideally is just a field on the product
    public void setUpc(int upc) {
        if (upc > 0)
            this.upc = upc;
        else
            this.upc = DEFAULT_UPC;
    }

    public void setVendorCode(int vendorCode) {
        if (vendorCode > 0)
            this.vendorCode = vendorCode;
        else
            this.vendorCode = DEFAULT_VENDOR_CODE;
    }

    public void setStoreOhQuantity(int storeOhQuantity) {
        if (storeOhQuantity > 0)
            this.storeOhQuantity = storeOhQuantity;
        else
            this.storeOhQuantity = DEFAULT_STORE_OH_QUANTITY;
    }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) > 0)
            this.price = price;
        else
            this.price = DEFAULT_PRICE;
    }

    public void setCost(BigDecimal cost) {
        if (cost.compareTo(BigDecimal.ZERO) > 0)
            this.cost = cost;
        else
            this.cost = DEFAULT_COST;
    }

    //TODO for desc I wonder if it would be better to do the non null check as an annotation
    public void setDesc1(String desc1) {
        if (desc1 != null && !desc1.isBlank())
            this.desc1 = desc1;
        else
            this.desc1 = DEFAULT_DESC1;
    }

    public void setDesc2(String desc2) {
        if (desc2 != null && !desc2.isBlank())
            this.desc2 = desc2;
        else
            this.desc2 = DEFAULT_DESC2;
    }

    public void setDateLastSold(LocalDateTime dateLastSold) {
        this.dateLastSold = dateLastSold;
    }
}
