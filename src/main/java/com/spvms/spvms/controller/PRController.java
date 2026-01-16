package com.spvms.spvms.controller;

import com.spvms.spvms.models.PRLineItem;
import com.spvms.spvms.models.PurchaseRequest;
import com.spvms.spvms.models.User;
import com.spvms.spvms.repository.PRRepository;
import com.spvms.spvms.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pr")
public class PRController {

    private final PRRepository prRepository;
    private final UserRepository userRepository;

    public PRController(PRRepository prRepository, UserRepository userRepository) {
        this.prRepository = prRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public PurchaseRequest create(@RequestBody PurchaseRequest pr) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        pr.setCreatedBy(user);
        pr.setStatus("PENDING");

        double total = 0;

        if (pr.getLineItems() != null) {
            for (PRLineItem item : pr.getLineItems()) {
                item.setPurchaseRequest(pr);
                total += item.getQuantity() * item.getEstimatedPrice();   // 🔥 calculate here
            }
        }

        pr.setEstimatedTotalCost(total);   // 🔥 set total

        return prRepository.save(pr);
    }

}
