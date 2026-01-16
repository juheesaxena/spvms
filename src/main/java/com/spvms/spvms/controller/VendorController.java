package com.spvms.spvms.controller;

import com.spvms.spvms.models.Vendor;
import com.spvms.spvms.models.VendorDocument;
import com.spvms.spvms.repository.VendorRepository;
import com.spvms.spvms.service.VendorComplianceService;
import com.spvms.spvms.service.VendorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorRepository vendorRepository;
    private final VendorComplianceService complianceService;
    private final VendorService vendorService;

    public VendorController(VendorRepository vendorRepository,
                            VendorComplianceService complianceService,
                            VendorService vendorService) {
        this.vendorRepository = vendorRepository;
        this.complianceService = complianceService;
        this.vendorService = vendorService;
    }

    @PostMapping
    public Vendor createVendor(@RequestBody Vendor vendor) {
        return vendorRepository.save(vendor);
    }



    @PostMapping(value = "/{id}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public VendorDocument uploadDocument(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type
    ) {
        return vendorService.uploadDocument(id, file, type);
    }

    @PutMapping("/{id}/compliance")
    public void updateCompliance(@PathVariable Long id,
                                 @RequestParam Boolean compliant,
                                 @RequestParam String remarks) {
        complianceService.updateCompliance(id, compliant, remarks);
    }
}
