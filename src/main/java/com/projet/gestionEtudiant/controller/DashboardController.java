package com.projet.gestionEtudiant.controller;

import com.projet.gestionEtudiant.repository.EmployeRepository;
import com.projet.gestionEtudiant.service.DepartementService;
import com.projet.gestionEtudiant.service.EmployeService;
import com.projet.gestionEtudiant.service.PosteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final EmployeService employeService;
    private final DepartementService departementService;
    private final PosteService posteService;
    private final EmployeRepository employeRepository;

    public DashboardController(EmployeService employeService, 
                               DepartementService departementService, 
                               PosteService posteService,
                               EmployeRepository employeRepository) {
        this.employeService = employeService;
        this.departementService = departementService;
        this.posteService = posteService;
        this.employeRepository = employeRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // الإحصائيات العامة
        model.addAttribute("totalEmployes", employeService.countEmployes());
        model.addAttribute("totalDepartements", departementService.countDepartements());
        model.addAttribute("totalPostes", posteService.countPostes());

        // 1. بيانات المبيان الدائري (الموظفون حسب القسم)
        List<Object[]> deptData = employeRepository.countEmployesByDepartement();
        List<String> deptLabels = deptData.stream().map(row -> (String) row[0]).collect(Collectors.toList());
        List<Long> deptCounts = deptData.stream().map(row -> (Long) row[1]).collect(Collectors.toList());

        // 2. بيانات مبيان الأعمدة (الموظفون حسب المنصب)
        List<Object[]> posteData = employeRepository.countEmployesByPoste();
        List<String> posteLabels = posteData.stream().map(row -> (String) row[0]).collect(Collectors.toList());
        List<Long> posteCounts = posteData.stream().map(row -> (Long) row[1]).collect(Collectors.toList());

        // تمرير البيانات لصفحة dashboard.html
        model.addAttribute("deptLabels", deptLabels);
        model.addAttribute("deptCounts", deptCounts);
        model.addAttribute("posteLabels", posteLabels);
        model.addAttribute("posteCounts", posteCounts);

        return "dashboard";
    }
}