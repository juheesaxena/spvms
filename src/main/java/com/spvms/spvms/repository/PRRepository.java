package com.spvms.spvms.repository;

import com.spvms.spvms.models.PurchaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PRRepository extends JpaRepository<PurchaseRequest, Long> {
}
