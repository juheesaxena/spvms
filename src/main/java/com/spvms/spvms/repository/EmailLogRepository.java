package com.spvms.spvms.repository;

import com.spvms.spvms.models.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {

    List<EmailLog> findByStatus(String status);
}
