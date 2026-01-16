package com.spvms.spvms.repository;

import com.spvms.spvms.models.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PORepository extends JpaRepository<PurchaseOrder, Long> {
}
