package com.spvms.spvms.models;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery_items")
public class DeliveryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private Integer deliveredQuantity;

    private Boolean damaged = false;
    private Boolean returned = false;

    private String remarks;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // getters & setters

    public Long getId() { return id; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Integer getDeliveredQuantity() { return deliveredQuantity; }
    public void setDeliveredQuantity(Integer deliveredQuantity) { this.deliveredQuantity = deliveredQuantity; }

    public Boolean getDamaged() { return damaged; }
    public void setDamaged(Boolean damaged) { this.damaged = damaged; }

    public Boolean getReturned() { return returned; }
    public void setReturned(Boolean returned) { this.returned = returned; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Delivery getDelivery() { return delivery; }
    public void setDelivery(Delivery delivery) { this.delivery = delivery; }
}
