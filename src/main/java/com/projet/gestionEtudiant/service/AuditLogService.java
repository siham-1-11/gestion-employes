package com.projet.gestionEtudiant.service;

import com.projet.gestionEtudiant.entity.AuditLog;
import org.springframework.data.domain.Page;

public interface AuditLogService {
    void logAction(String action, String entityName, Long entityId, String username, String details);
    Page<AuditLog> getLogs(int page, int size);
}
