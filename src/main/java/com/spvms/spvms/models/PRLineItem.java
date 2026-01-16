package com.spvms.spvms.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "pr_line_items")
public class PRLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private Integer quantity;
    private Double estimatedPrice;
    private Integer displayOrder;

    @ManyToOne
    @JoinColumn(name = "pr_id", nullable = false)
    @JsonIgnore   // 🔴 VERY IMPORTANT – stops loop
    private PurchaseRequest purchaseRequest;

    @OneToMany(mappedBy = "prLineItem")
    @JsonIgnore   // 🔴 VERY IMPORTANT – stops loop
    private List<VendorQuote> vendorQuotes;

    @ManyToOne
    @JoinColumn(name = "selected_quote_id")
    private VendorQuote selectedQuote;

    public VendorQuote getSelectedQuote() {
        return selectedQuote;
    }

    public void setSelectedQuote(VendorQuote selectedQuote) {
        this.selectedQuote = selectedQuote;
    }

    // ===== Getters & Setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getEstimatedPrice() { return estimatedPrice; }
    public void setEstimatedPrice(Double estimatedPrice) { this.estimatedPrice = estimatedPrice; }

    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }

    public PurchaseRequest getPurchaseRequest() { return purchaseRequest; }
    public void setPurchaseRequest(PurchaseRequest purchaseRequest) { this.purchaseRequest = purchaseRequest; }

    public List<VendorQuote> getVendorQuotes() { return vendorQuotes; }
    public void setVendorQuotes(List<VendorQuote> vendorQuotes) { this.vendorQuotes = vendorQuotes; }
}
