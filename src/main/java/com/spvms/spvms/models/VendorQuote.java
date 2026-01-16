package com.spvms.spvms.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "vendor_quotes")
public class VendorQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double quotedPrice;

    @ManyToOne
    @JoinColumn(name = "vendor_id", nullable = false)
    @JsonIgnore   // 🔴 stops loop
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "pr_line_item_id", nullable = false)
    @JsonIgnore   // 🔴 stops loop
    private PRLineItem prLineItem;

    // ===== Getters & Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getQuotedPrice() { return quotedPrice; }
    public void setQuotedPrice(Double quotedPrice) { this.quotedPrice = quotedPrice; }

    public Vendor getVendor() { return vendor; }
    public void setVendor(Vendor vendor) { this.vendor = vendor; }

    public PRLineItem getPrLineItem() { return prLineItem; }
    public void setPrLineItem(PRLineItem prLineItem) { this.prLineItem = prLineItem; }
}
