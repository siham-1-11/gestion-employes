package com.projet.gestionEtudiant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "departements")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du département est obligatoire")
    @Column(nullable = false, unique = true)
    private String nom;

    private String description;
}