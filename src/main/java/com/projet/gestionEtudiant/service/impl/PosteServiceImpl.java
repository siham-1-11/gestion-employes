package com.projet.gestionEtudiant.service.impl;

import com.projet.gestionEtudiant.entity.Poste;
import com.projet.gestionEtudiant.repository.PosteRepository;
import com.projet.gestionEtudiant.service.PosteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosteServiceImpl implements PosteService {

    private final PosteRepository posteRepository;

    public PosteServiceImpl(PosteRepository posteRepository) {
        this.posteRepository = posteRepository;
    }

    @Override
    public List<Poste> getAllPostes() {
        return posteRepository.findAll();
    }

    @Override
    public Poste getPosteById(Long id) {
        return posteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poste introuvable"));
    }

    @Override
    public Poste savePoste(Poste poste) {
        return posteRepository.save(poste);
    }

    @Override
    public void deletePoste(Long id) {
        posteRepository.deleteById(id);
    }

    @Override
    public long countPostes() {
        return posteRepository.count();
    }
}