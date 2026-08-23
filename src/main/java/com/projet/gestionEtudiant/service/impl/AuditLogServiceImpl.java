package com.projet.gestionEtudiant.service.impl;

import com.projet.gestionEtudiant.entity.AuditLog;
import com.projet.gestionEtudiant.repository.AuditLogRepository;
import com.projet.gestionEtudiant.service.AuditLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void logAction(String action, String entityName, Long entityId, String username, String details) {
        AuditLog log = new AuditLog(action, entityName, entityId, username, details);
        auditLogRepository.save(log);
    }

    @Override
    public Page<AuditLog> getLogs(int page, int size) {
        return auditLogRepository.findAllByOrderByTimestampDesc(PageRequest.of(page, size));
    }
}