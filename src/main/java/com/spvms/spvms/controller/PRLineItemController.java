package com.spvms.spvms.controller;

import com.spvms.spvms.models.PRLineItem;
import com.spvms.spvms.service.PRLineItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pr-items")
public class PRLineItemController {

    private final PRLineItemService service;

    public PRLineItemController(PRLineItemService service) {
        this.service = service;
    }
    @PutMapping("/{lineItemId}/select-quote/{quoteId}")
    public PRLineItem selectQuote(@PathVariable Long lineItemId,
                                  @PathVariable Long quoteId) {
        return service.selectQuote(lineItemId, quoteId);
    }


    @PostMapping("/{prId}")
    public PRLineItem add(@PathVariable Long prId, @RequestBody PRLineItem item) {
        return service.addItem(prId, item);
    }

    @DeleteMapping("/{itemId}")
    public void delete(@PathVariable Long itemId) {
        service.deleteItem(itemId);
    }

    @PutMapping("/reorder/{prId}")
    public void reorder(@PathVariable Long prId, @RequestBody List<Long> ids) {
        service.reorder(prId, ids);
    }
}
