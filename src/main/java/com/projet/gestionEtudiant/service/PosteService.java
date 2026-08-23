package com.projet.gestionEtudiant.service;

import com.projet.gestionEtudiant.entity.Poste;
import java.util.List;

public interface PosteService {
    List<Poste> getAllPostes();
    Poste getPosteById(Long id);
    Poste savePoste(Poste poste);
    void deletePoste(Long id);

    long countPostes(); // <--- الدالة الجديدة لحساب عدد الوظائف
}