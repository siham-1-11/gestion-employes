package com.projet.gestionEtudiant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "postes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Poste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le intitulé du poste est obligatoire")
    @Column(nullable = false, unique = true)
    private String intitule;

    private String description;
}