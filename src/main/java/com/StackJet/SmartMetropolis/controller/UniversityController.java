package com.StackJet.SmartMetropolis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.StackJet.SmartMetropolis.entity.University;
import com.StackJet.SmartMetropolis.service.UniversityService;

@Controller
public class UniversityController {

    @Autowired
    private UniversityService universityService;

    @GetMapping("/view_university")
    public String showUniversityView(Model model) {
        List<University> universities = universityService.getAllUniversities();
        model.addAttribute("universities", universities);
        return "view_university"; // Ensure thsis matches the name of your HTML file
    }

    @GetMapping("/add_university")
    public String addUniversityForm(Model model) {
        model.addAttribute("university", new University());
        return "add_university"; // Ensure this matches the name of your HTML file
    }

    @PostMapping("/add_university")
    public String addUniversity(@ModelAttribute University university, @RequestParam("image") MultipartFile image, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_university"; // Return to form with errors
        }
        universityService.saveUniversity(university, image);
        return "redirect:/view_university"; // Redirect to list view
    }

    @GetMapping("/edit_university")
    public String editUniversityForm(@RequestParam("id") Long id, Model model) {
        University university = universityService.getUniversityById(id);
        if (university != null) {
            model.addAttribute("university", university);
            return "edit_university"; // Ensure this matches the name of your HTML file
        } else {
            return "redirect:/view_university"; // Redirect if university not found
        }
    }

    @PostMapping("/update_university")
    public String updateUniversity(@ModelAttribute University university, @RequestParam("image") MultipartFile image) {
        universityService.saveUniversity(university, image);
        return "redirect:/view_university"; // Redirect to a page that lists all universities or shows a confirmation
    }

    @PostMapping("/delete_university")
    public String deleteUniversity(@RequestParam("id") Long id) {
        universityService.deleteUniversity(id);
        return "redirect:/view_university";
    }
    @GetMapping("/colleges")
    public String viewUniversities(Model model) {
        // Fetch all universities from the service
        model.addAttribute("universities", universityService.getAllUniversities());
        return "colleges"; // Name of the Thymeleaf template, e.g., colleges.html
    }
}
