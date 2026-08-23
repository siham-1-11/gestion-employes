package com.projet.gestionEtudiant.repository;

import com.projet.gestionEtudiant.entity.Employe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {

    // Recherche multi-critères (Nom, Prénom, Email) + Filtrage b-Poste/Département + Pagination
    @Query("SELECT e FROM Employe e WHERE " +
           "(:keyword IS NULL OR :keyword = '' OR LOWER(e.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(e.prenom) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(e.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:departementId IS NULL OR e.departement.id = :departementId) AND " +
           "(:posteId IS NULL OR e.poste.id = :posteId)")
    Page<Employe> searchAndFilter(@Param("keyword") String keyword,
                                 @Param("departementId") Long departementId,
                                 @Param("posteId") Long posteId,
                                 Pageable pageable);

    // حساب عدد الموظفين لكل قسم (من أجل المبيان الدائري)
    @Query("SELECT e.departement.nom, COUNT(e) FROM Employe e GROUP BY e.departement.nom")
    List<Object[]> countEmployesByDepartement();

    // حساب عدد الموظفين لكل منصب (من أجل مبيان الأعمدة)
    @Query("SELECT e.poste.intitule, COUNT(e) FROM Employe e GROUP BY e.poste.intitule")
    List<Object[]> countEmployesByPoste();
}