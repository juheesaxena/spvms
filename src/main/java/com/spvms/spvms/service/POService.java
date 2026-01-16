package com.spvms.spvms.service;

import com.spvms.spvms.models.PurchaseOrder;
import com.spvms.spvms.repository.PORepository;
import org.springframework.stereotype.Service;

@Service
public class POService {

    private final PORepository poRepository;

    public POService(PORepository poRepository) {
        this.poRepository = poRepository;
    }

    public PurchaseOrder createPO(PurchaseOrder po) {
        po.setStatus("CREATED");
        return poRepository.save(po);
    }

    public PurchaseOrder updateStatus(Long id, String status) {
        PurchaseOrder po = poRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PO not found"));
        po.setStatus(status);
        return poRepository.save(po);
    }
}
