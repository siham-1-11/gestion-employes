package com.projet.gestionEtudiant.service.impl;

import com.projet.gestionEtudiant.entity.Employe;
import com.projet.gestionEtudiant.repository.EmployeRepository;
import com.projet.gestionEtudiant.service.AuditLogService;
import com.projet.gestionEtudiant.service.EmployeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeServiceImpl implements EmployeService {

    private final EmployeRepository employeRepository;
    private final AuditLogService auditLogService;
    private final String UPLOAD_DIR = "uploads/";

    // تم حقن AuditLogService هنا
    public EmployeServiceImpl(EmployeRepository employeRepository, AuditLogService auditLogService) {
        this.employeRepository = employeRepository;
        this.auditLogService = auditLogService;
    }

    // دالة للحصول على اسم المستخدم الحالي المتصل عبر Spring Security
    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated()) ? auth.getName() : "SYSTEM";
    }

    @Override
    public Page<Employe> getEmployes(String keyword, Long departementId, Long posteId, int page, int size, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
                    Sort.by(sortField).ascending() : 
                    Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return employeRepository.searchAndFilter(keyword, departementId, posteId, pageable);
    }

    @Override
    public Employe getEmployeById(Long id) {
        return employeRepository.findById(id).orElse(null);
    }

    @Override
    public Employe saveEmploye(Employe employe, MultipartFile photoFile) {
        boolean isNew = (employe.getId() == null);

        if (photoFile != null && !photoFile.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + photoFile.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Files.copy(photoFile.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                employe.setPhoto(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (!isNew) {
            Employe existing = getEmployeById(employe.getId());
            if (existing != null) {
                employe.setPhoto(existing.getPhoto());
            }
        }

        Employe saved = employeRepository.save(employe);

        // تسجيل العملية في السجل (إضافة أو تعديل)
        String action = isNew ? "CREATION" : "MODIFICATION";
        String details = "Employé: " + saved.getNom() + " " + saved.getPrenom() + " (" + saved.getEmail() + ")";
        auditLogService.logAction(action, "Employe", saved.getId(), getCurrentUsername(), details);

        return saved;
    }

    @Override
    public void deleteEmploye(Long id) {
        Employe existing = getEmployeById(id);
        if (existing != null) {
            employeRepository.deleteById(id);
            // تسجيل عملية الحذف في السجل
            String details = "Suppression de l'employé: " + existing.getNom() + " " + existing.getPrenom();
            auditLogService.logAction("SUPPRESSION", "Employe", id, getCurrentUsername(), details);
        }
    }

    @Override
    public long countEmployes() {
        return employeRepository.count();
    }

    @Override
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }
}