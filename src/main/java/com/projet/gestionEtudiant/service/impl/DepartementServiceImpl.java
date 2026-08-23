package com.projet.gestionEtudiant.service.impl;

import com.projet.gestionEtudiant.entity.Departement;
import com.projet.gestionEtudiant.repository.DepartementRepository;
import com.projet.gestionEtudiant.service.DepartementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementServiceImpl implements DepartementService {

    private final DepartementRepository departementRepository;

    public DepartementServiceImpl(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    @Override
    public List<Departement> getAllDepartements() {
        return departementRepository.findAll();
    }

    @Override
    public Departement getDepartementById(Long id) {
        return departementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Département introuvable"));
    }

    @Override
    public Departement saveDepartement(Departement departement) {
        return departementRepository.save(departement);
    }

    @Override
    public void deleteDepartement(Long id) {
        departementRepository.deleteById(id);
    }

    @Override
    public long countDepartements() {
        return departementRepository.count();
    }
}