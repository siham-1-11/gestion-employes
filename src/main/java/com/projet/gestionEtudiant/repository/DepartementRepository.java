package com.projet.gestionEtudiant.repository;

import com.projet.gestionEtudiant.entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
}