package com.spvms.spvms.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate expectedDate;
    private LocalDate deliveredDate;

    private String status; // PENDING, PARTIAL, COMPLETED

    @ManyToOne
    @JoinColumn(name = "po_id")
    private PurchaseOrder purchaseOrder;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryItem> items = new ArrayList<>();

    // getters & setters

    public Long getId() { return id; }

    public LocalDate getExpectedDate() { return expectedDate; }
    public void setExpectedDate(LocalDate expectedDate) { this.expectedDate = expectedDate; }

    public LocalDate getDeliveredDate() { return deliveredDate; }
    public void setDeliveredDate(LocalDate deliveredDate) { this.deliveredDate = deliveredDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public PurchaseOrder getPurchaseOrder() { return purchaseOrder; }
    public void setPurchaseOrder(PurchaseOrder purchaseOrder) { this.purchaseOrder = purchaseOrder; }

    public List<DeliveryItem> getItems() { return items; }
    public void setItems(List<DeliveryItem> items) { this.items = items; }
}
