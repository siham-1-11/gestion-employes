package com.projet.gestionEtudiant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^$|^(\\+212|0)[5-7][0-9]{8}$", message = "Format de téléphone invalide (ex: 0612345678)")
    private String telephone;

    private String photo;

    // إضافة @NotNull لمنع حفظ الموظف بدون تحديد الوظيفة
    @NotNull(message = "Veuillez sélectionner un poste")
    @ManyToOne
    @JoinColumn(name = "poste_id")
    private Poste poste;

    // إضافة @NotNull لمنع حفظ الموظف بدون تحديد القسم
    @NotNull(message = "Veuillez sélectionner un département")
    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;
}