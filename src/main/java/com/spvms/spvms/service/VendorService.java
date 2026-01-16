package com.spvms.spvms.service;

import com.spvms.spvms.models.Vendor;
import com.spvms.spvms.models.VendorDocument;
import com.spvms.spvms.repository.VendorDocumentRepository;
import com.spvms.spvms.repository.VendorRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final VendorDocumentRepository vendorDocumentRepository;   // ✅ ADD THIS

    public VendorService(VendorRepository vendorRepository,
                         VendorDocumentRepository vendorDocumentRepository) { // ✅ ADD THIS
        this.vendorRepository = vendorRepository;
        this.vendorDocumentRepository = vendorDocumentRepository;
    }

    public Vendor addVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor markCompliant(Long vendorId, boolean compliant) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        vendor.setCompliant(compliant);
        return vendorRepository.save(vendor);
    }


    public VendorDocument uploadDocument(Long vendorId, MultipartFile file, String type) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        try {
            // Create folder: uploads/vendors/{vendorId}
            String baseDir = "uploads/vendors/" + vendorId;
            File dir = new File(baseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Save file
            String filePath = baseDir + "/" + file.getOriginalFilename();
            Path path = Paths.get(filePath);
            Files.write(path, file.getBytes());

            // Save in DB
            VendorDocument doc = new VendorDocument();
            doc.setVendor(vendor);
            doc.setDocumentType(type);
            doc.setFilePath(filePath);

            return vendorDocumentRepository.save(doc);   // ✅ FIXED LINE

        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }
    }
}
