package com.projet.gestionEtudiant.repository;

import com.projet.gestionEtudiant.entity.Poste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosteRepository extends JpaRepository<Poste, Long> {
}