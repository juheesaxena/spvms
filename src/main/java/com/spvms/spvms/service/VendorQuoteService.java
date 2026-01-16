package com.spvms.spvms.service;

import com.spvms.spvms.models.PRLineItem;
import com.spvms.spvms.models.Vendor;
import com.spvms.spvms.models.VendorQuote;
import com.spvms.spvms.repository.PRLineItemRepository;
import com.spvms.spvms.repository.VendorQuoteRepository;
import com.spvms.spvms.repository.VendorRepository;
import org.springframework.stereotype.Service;

@Service
public class VendorQuoteService {

    private final VendorQuoteRepository quoteRepository;
    private final VendorRepository vendorRepository;
    private final PRLineItemRepository lineItemRepository;

    public VendorQuoteService(VendorQuoteRepository quoteRepository,
                              VendorRepository vendorRepository,
                              PRLineItemRepository lineItemRepository) {
        this.quoteRepository = quoteRepository;
        this.vendorRepository = vendorRepository;
        this.lineItemRepository = lineItemRepository;
    }

    public VendorQuote addQuote(Long lineItemId, Long vendorId, Double price) {

        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + vendorId));

        PRLineItem lineItem = lineItemRepository.findById(lineItemId)
                .orElseThrow(() -> new RuntimeException("PR Line Item not found with id: " + lineItemId));

        VendorQuote quote = new VendorQuote();
        quote.setVendor(vendor);
        quote.setPrLineItem(lineItem);
        quote.setQuotedPrice(price);

        return quoteRepository.save(quote);
    }
}
