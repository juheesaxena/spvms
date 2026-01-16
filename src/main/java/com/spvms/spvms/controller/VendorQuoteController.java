package com.spvms.spvms.controller;

import com.spvms.spvms.models.VendorQuote;
import com.spvms.spvms.service.VendorQuoteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendor-quotes")
public class VendorQuoteController {

    private final VendorQuoteService vendorQuoteService;

    public VendorQuoteController(VendorQuoteService vendorQuoteService) {
        this.vendorQuoteService = vendorQuoteService;
    }

    @PostMapping
    @PreAuthorize("hasRole('VENDOR')")
    public String addQuote(@RequestParam Long lineItemId,
                           @RequestParam Long vendorId,
                           @RequestParam Double price) {

        vendorQuoteService.addQuote(lineItemId, vendorId, price);
        return "Quote added successfully";
    }

}
