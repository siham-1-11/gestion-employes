package com.projet.gestionEtudiant.controller;

import com.projet.gestionEtudiant.entity.Departement;
import com.projet.gestionEtudiant.service.DepartementService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departements")
public class DepartementController {

    private final DepartementService departementService;

    public DepartementController(DepartementService departementService) {
        this.departementService = departementService;
    }

    @GetMapping
    public String listDepartements(Model model) {
        model.addAttribute("departements", departementService.getAllDepartements());
        return "departements/index";
    }

    @GetMapping("/ajouter")
    public String showAddForm(Model model) {
        model.addAttribute("departement", new Departement());
        return "departements/form";
    }

    @PostMapping("/save")
    public String saveDepartement(@Valid @ModelAttribute("departement") Departement departement, 
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "departements/form";
        }
        departementService.saveDepartement(departement);
        return "redirect:/departements";
    }

    @GetMapping("/modifier/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("departement", departementService.getDepartementById(id));
        return "departements/form";
    }

    @GetMapping("/supprimer/{id}")
    public String deleteDepartement(@PathVariable Long id) {
        departementService.deleteDepartement(id);
        return "redirect:/departements";
    }
}