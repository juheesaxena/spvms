package com.spvms.spvms.controller;

import com.spvms.spvms.models.VendorDocument;
import com.spvms.spvms.service.VendorDocumentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/vendors/{vendorId}/documents")
public class VendorDocumentController {

    private final VendorDocumentService service;

    public VendorDocumentController(VendorDocumentService service) {
        this.service = service;
    }

    @PostMapping
    public VendorDocument upload(@PathVariable Long vendorId,
                                 @RequestParam String type,
                                 @RequestParam MultipartFile file) throws Exception {

        return service.uploadDocument(vendorId, type, file);
    }
}
