package com.spvms.spvms.service;

import com.spvms.spvms.models.Vendor;
import com.spvms.spvms.models.VendorDocument;
import com.spvms.spvms.repository.VendorDocumentRepository;
import com.spvms.spvms.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class VendorDocumentService {

    private final VendorDocumentRepository documentRepository;
    private final VendorRepository vendorRepository;

    public VendorDocumentService(VendorDocumentRepository documentRepository,
                                 VendorRepository vendorRepository) {
        this.documentRepository = documentRepository;
        this.vendorRepository = vendorRepository;
    }

    public VendorDocument uploadDocument(Long vendorId, String type, MultipartFile file) throws IOException {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        String uploadDir = "uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        VendorDocument doc = new VendorDocument();
        doc.setDocumentType(type);
        doc.setFilePath(filePath);
        doc.setVendor(vendor);

        return documentRepository.save(doc);
    }
}
