package com.projet.gestionEtudiant.service;

import com.projet.gestionEtudiant.entity.Departement;
import java.util.List;

public interface DepartementService {
    List<Departement> getAllDepartements();
    Departement getDepartementById(Long id);
    Departement saveDepartement(Departement departement);
    void deleteDepartement(Long id);

    long countDepartements(); // <--- الدالة الجديدة لحساب عدد القسم
}