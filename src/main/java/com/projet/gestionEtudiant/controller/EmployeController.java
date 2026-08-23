package com.projet.gestionEtudiant.controller;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.projet.gestionEtudiant.entity.Employe;
import com.projet.gestionEtudiant.service.DepartementService;
import com.projet.gestionEtudiant.service.EmployeService;
import com.projet.gestionEtudiant.service.PosteService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/employes")
public class EmployeController {

    private final EmployeService employeService;
    private final DepartementService departementService;
    private final PosteService posteService;

    public EmployeController(EmployeService employeService, DepartementService departementService, PosteService posteService) {
        this.employeService = employeService;
        this.departementService = departementService;
        this.posteService = posteService;
    }

    @GetMapping
    public String listEmployes(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Long departementId,
            @RequestParam(required = false) Long posteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "nom") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Page<Employe> employesPage = employeService.getEmployes(keyword, departementId, posteId, page, size, sortField, sortDir);

        model.addAttribute("employes", employesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employesPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("departementId", departementId);
        model.addAttribute("posteId", posteId);

        // إضافة بيانات الترتيب للـ Model
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("departements", departementService.getAllDepartements());
        model.addAttribute("postes", posteService.getAllPostes());

        return "employes/index";
    }

    @GetMapping("/ajouter")
    public String showAddForm(Model model) {
        model.addAttribute("employe", new Employe());
        model.addAttribute("departements", departementService.getAllDepartements());
        model.addAttribute("postes", posteService.getAllPostes());
        return "employes/form";
    }

    @PostMapping("/save")
    public String saveEmploye(
            @Valid @ModelAttribute("employe") Employe employe,
            BindingResult result,
            @RequestParam("photoFile") MultipartFile photoFile,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("departements", departementService.getAllDepartements());
            model.addAttribute("postes", posteService.getAllPostes());
            return "employes/form";
        }

        try {
            employeService.saveEmploye(employe, photoFile);
        } catch (Exception e) {
            result.rejectValue("email", "error.employe", "Cet email est déjà utilisé par un autre employé");
            model.addAttribute("departements", departementService.getAllDepartements());
            model.addAttribute("postes", posteService.getAllPostes());
            return "employes/form";
        }

        return "redirect:/employes";
    }

    @GetMapping("/modifier/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("employe", employeService.getEmployeById(id));
        model.addAttribute("departements", departementService.getAllDepartements());
        model.addAttribute("postes", posteService.getAllPostes());
        return "employes/form";
    }

    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable Long id, Model model) {
        model.addAttribute("employe", employeService.getEmployeById(id));
        return "employes/details";
    }

    @GetMapping("/supprimer/{id}")
    public String deleteEmploye(@PathVariable Long id) {
        employeService.deleteEmploye(id);
        return "redirect:/employes";
    }

    // --- Exporter Excel ---
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=employes.xlsx");

        List<Employe> listEmployes = employeService.getAllEmployes();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employés");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Prénom");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Département");
        headerRow.createCell(5).setCellValue("Poste");

        int rowNum = 1;
        for (Employe emp : listEmployes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(emp.getId());
            row.createCell(1).setCellValue(emp.getNom());
            row.createCell(2).setCellValue(emp.getPrenom());
            row.createCell(3).setCellValue(emp.getEmail());
            row.createCell(4).setCellValue(emp.getDepartement() != null ? emp.getDepartement().getNom() : "");
            row.createCell(5).setCellValue(emp.getPoste() != null ? emp.getPoste().getIntitule() : "");
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // --- Exporter PDF ---
    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=employes.pdf");

        List<Employe> listEmployes = employeService.getAllEmployes();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        document.add(new Paragraph("Liste des Employés"));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.addCell("ID");
        table.addCell("Nom");
        table.addCell("Prénom");
        table.addCell("Email");
        table.addCell("Département");
        table.addCell("Poste");

        for (Employe emp : listEmployes) {
            table.addCell(String.valueOf(emp.getId()));
            table.addCell(emp.getNom());
            table.addCell(emp.getPrenom());
            table.addCell(emp.getEmail());
            table.addCell(emp.getDepartement() != null ? emp.getDepartement().getNom() : "");
            table.addCell(emp.getPoste() != null ? emp.getPoste().getIntitule() : "");
        }

        document.add(table);
        document.close();
    }
}