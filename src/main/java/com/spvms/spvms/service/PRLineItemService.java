package com.spvms.spvms.service;

import com.spvms.spvms.models.PRLineItem;
import com.spvms.spvms.models.PurchaseRequest;
import com.spvms.spvms.models.VendorQuote;
import com.spvms.spvms.repository.PRLineItemRepository;
import com.spvms.spvms.repository.PRRepository;
import com.spvms.spvms.repository.VendorQuoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PRLineItemService {

    private final PRLineItemRepository itemRepo;
    private final PRRepository prRepo;
    private final VendorQuoteRepository quoteRepository;

    public PRLineItemService(PRLineItemRepository itemRepo, PRRepository prRepo,VendorQuoteRepository quoteRepository) {
        this.itemRepo = itemRepo;
        this.prRepo = prRepo;
        this.quoteRepository = quoteRepository;
    }

    public PRLineItem addItem(Long prId, PRLineItem item) {

        if (item.getQuantity() <= 0 || item.getEstimatedPrice() <= 0) {
            throw new RuntimeException("Quantity and price must be > 0");
        }

        PurchaseRequest pr = prRepo.findById(prId)
                .orElseThrow(() -> new RuntimeException("PR not found"));

        item.setPurchaseRequest(pr);

        int order = itemRepo.findByPurchaseRequestIdOrderByDisplayOrder(prId).size() + 1;
        item.setDisplayOrder(order);

        PRLineItem saved = itemRepo.save(item);
        recalcTotal(prId);

        return saved;
    }

    public PRLineItem selectQuote(Long lineItemId, Long quoteId) {

        PRLineItem item = itemRepo.findById(lineItemId)
                .orElseThrow(() -> new RuntimeException("Line item not found"));

        VendorQuote quote =quoteRepository.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("Quote not found"));

        item.setSelectedQuote(quote);
        return itemRepo.save(item);
    }


    public void deleteItem(Long itemId) {
        PRLineItem item = itemRepo.findById(itemId).orElseThrow();
        Long prId = item.getPurchaseRequest().getId();
        itemRepo.delete(item);
        recalcTotal(prId);
    }

    public void reorder(Long prId, List<Long> orderedIds) {
        List<PRLineItem> items = itemRepo.findByPurchaseRequestIdOrderByDisplayOrder(prId);

        int order = 1;
        for (Long id : orderedIds) {
            for (PRLineItem item : items) {
                if (item.getId().equals(id)) {
                    item.setDisplayOrder(order++);
                    itemRepo.save(item);
                }
            }
        }
    }

    private void recalcTotal(Long prId) {
        PurchaseRequest pr = prRepo.findById(prId).orElseThrow();

        double total = 0;
        for (PRLineItem item : itemRepo.findByPurchaseRequestIdOrderByDisplayOrder(prId)) {
            total += item.getQuantity() * item.getEstimatedPrice();
        }

        pr.setEstimatedTotalCost(total);
        prRepo.save(pr);
    }
}
