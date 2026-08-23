package com.projet.gestionEtudiant.service;

import com.projet.gestionEtudiant.entity.Employe;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeService {
    Page<Employe> getEmployes(String keyword, Long departementId, Long posteId, int page, int size, String sortField, String sortDir);
    Employe getEmployeById(Long id);
    Employe saveEmploye(Employe employe, MultipartFile photoFile);
    void deleteEmploye(Long id);
    
    long countEmployes();
    List<Employe> getAllEmployes();
}