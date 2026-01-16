package com.spvms.spvms.service;

import com.spvms.spvms.models.PurchaseRequest;
import com.spvms.spvms.models.User;
import com.spvms.spvms.repository.PRRepository;
import com.spvms.spvms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PurchaseRequestService {

    private final PRRepository prRepository;
    private final UserRepository userRepository;

    public PurchaseRequestService(PRRepository prRepository,
                                  UserRepository userRepository) {
        this.prRepository = prRepository;
        this.userRepository = userRepository;
    }

    public PurchaseRequest createPR(PurchaseRequest pr) {

        // Generate PR number
        String prNumber = "PR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        pr.setPrNumber(prNumber);

        pr.setStatus("PENDING");

        return prRepository.save(pr);
    }

    public PurchaseRequest approvePR(Long id) {

        PurchaseRequest pr = prRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PR not found"));

        pr.setStatus("APPROVED");

        return prRepository.save(pr);
    }
}
