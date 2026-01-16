package com.spvms.spvms.service;

import com.spvms.spvms.models.*;
import com.spvms.spvms.repository.DeliveryRepository;
import com.spvms.spvms.repository.PORepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepo;
    private final PORepository poRepo;

    public DeliveryService(DeliveryRepository deliveryRepo,
                           PORepository poRepo) {
        this.deliveryRepo = deliveryRepo;
        this.poRepo = poRepo;
    }

    // Create delivery (expected date)
    public Delivery createDelivery(Long poId, LocalDate expectedDate) {

        PurchaseOrder po = poRepo.findById(poId).orElseThrow();

        Delivery delivery = new Delivery();
        delivery.setPurchaseOrder(po);
        delivery.setExpectedDate(expectedDate);
        delivery.setStatus("PENDING");

        po.setStatus("IN_DELIVERY"); // update PO status
        poRepo.save(po);

        return deliveryRepo.save(delivery);
    }

    // Confirm delivery (partial / complete)
    public Delivery confirmDelivery(Long deliveryId, Delivery deliveryData) {

        Delivery delivery = deliveryRepo.findById(deliveryId).orElseThrow();

        delivery.setDeliveredDate(LocalDate.now());
        delivery.setItems(deliveryData.getItems());

        // attach delivery reference to each item
        for (DeliveryItem item : delivery.getItems()) {
            item.setDelivery(delivery);
        }

        boolean hasPartial = delivery.getItems().stream()
                .anyMatch(i -> i.getReturned() || i.getDamaged());

        if (hasPartial) {
            delivery.setStatus("PARTIAL");
            delivery.getPurchaseOrder().setStatus("PARTIAL_DELIVERED");
        } else {
            delivery.setStatus("COMPLETED");
            delivery.getPurchaseOrder().setStatus("DELIVERED");
        }

        poRepo.save(delivery.getPurchaseOrder());

        return deliveryRepo.save(delivery);
    }
}
