package com.spvms.spvms.repository;

import com.spvms.spvms.models.ComplianceStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplianceHistoryRepository extends JpaRepository<ComplianceStatusHistory, Long> {
}
