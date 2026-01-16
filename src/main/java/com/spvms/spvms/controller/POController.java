package com.spvms.spvms.controller;

import com.spvms.spvms.models.PurchaseOrder;
import com.spvms.spvms.service.POService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/po")
public class POController {

    private final POService poService;

    public POController(POService poService) {
        this.poService = poService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PurchaseOrder create(@RequestBody PurchaseOrder po) {
        return poService.createPO(po);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FINANCE')")
    public PurchaseOrder updateStatus(@PathVariable Long id,
                                      @RequestParam String status) {
        return poService.updateStatus(id, status);
    }
}
