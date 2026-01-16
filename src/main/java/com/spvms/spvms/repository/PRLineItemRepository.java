package com.spvms.spvms.repository;

import com.spvms.spvms.models.PRLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PRLineItemRepository extends JpaRepository<PRLineItem, Long> {
    List<PRLineItem> findByPurchaseRequestIdOrderByDisplayOrder(Long prId);
}
