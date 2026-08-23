package com.projet.gestionEtudiant.controller;

import com.projet.gestionEtudiant.entity.Poste;
import com.projet.gestionEtudiant.service.PosteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/postes")
public class PosteController {

    private final PosteService posteService;

    public PosteController(PosteService posteService) {
        this.posteService = posteService;
    }

    @GetMapping
    public String listPostes(Model model) {
        model.addAttribute("postes", posteService.getAllPostes());
        return "postes/index";
    }

    @GetMapping("/ajouter")
    public String showAddForm(Model model) {
        model.addAttribute("poste", new Poste());
        return "postes/form";
    }

    @PostMapping("/save")
    public String savePoste(@Valid @ModelAttribute("poste") Poste poste, 
                           BindingResult result) {
        if (result.hasErrors()) {
            return "postes/form";
        }
        posteService.savePoste(poste);
        return "redirect:/postes";
    }

    @GetMapping("/modifier/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("poste", posteService.getPosteById(id));
        return "postes/form";
    }

    @GetMapping("/supprimer/{id}")
    public String deletePoste(@PathVariable Long id) {
        posteService.deletePoste(id);
        return "redirect:/postes";
    }
}