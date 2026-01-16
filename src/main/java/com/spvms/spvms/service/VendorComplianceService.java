package com.spvms.spvms.service;

import com.spvms.spvms.models.ComplianceStatusHistory;
import com.spvms.spvms.models.Vendor;
import com.spvms.spvms.repository.ComplianceHistoryRepository;
import com.spvms.spvms.repository.VendorRepository;
import org.springframework.stereotype.Service;

@Service
public class VendorComplianceService {

    private final VendorRepository vendorRepository;
    private final ComplianceHistoryRepository complianceHistoryRepository;

    public VendorComplianceService(VendorRepository vendorRepository,
                                   ComplianceHistoryRepository complianceHistoryRepository) {
        this.vendorRepository = vendorRepository;
        this.complianceHistoryRepository = complianceHistoryRepository;
    }

    public void updateCompliance(Long vendorId, Boolean compliant, String remarks) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendor.setCompliant(compliant);
        vendorRepository.save(vendor);

        ComplianceStatusHistory history = new ComplianceStatusHistory();
        history.setVendor(vendor);
        history.setCompliant(compliant);
        history.setRemarks(remarks);

        complianceHistoryRepository.save(history);
    }
}
