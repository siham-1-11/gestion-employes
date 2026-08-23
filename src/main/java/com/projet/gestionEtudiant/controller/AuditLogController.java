package com.projet.gestionEtudiant.controller;

import com.projet.gestionEtudiant.entity.AuditLog;
import com.projet.gestionEtudiant.service.AuditLogService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping("/audit-logs")
    public String listLogs(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           Model model) {
        Page<AuditLog> logPage = auditLogService.getLogs(page, size);
        model.addAttribute("logs", logPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", logPage.getTotalPages());
        return "audit/logs";
    }
}