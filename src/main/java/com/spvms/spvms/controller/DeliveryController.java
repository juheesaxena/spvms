package com.spvms.spvms.controller;

import com.spvms.spvms.models.Delivery;
import com.spvms.spvms.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService service;

    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    // Create delivery schedule
    @PostMapping("/po/{poId}")
    public Delivery create(@PathVariable Long poId,
                           @RequestParam LocalDate expectedDate) {
        return service.createDelivery(poId, expectedDate);
    }

    // Confirm delivery (partial / full)
    @PostMapping("/{deliveryId}/confirm")
    public Delivery confirm(@PathVariable Long deliveryId,
                            @RequestBody Delivery delivery) {
        return service.confirmDelivery(deliveryId, delivery);
    }
}
