package com.spvms.spvms.repository;

import com.spvms.spvms.models.VendorComplianceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorComplianceHistoryRepository extends JpaRepository<VendorComplianceHistory, Long> {
}
