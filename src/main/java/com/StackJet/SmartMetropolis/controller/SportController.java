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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.StackJet.SmartMetropolis.entity.Sport;
import com.StackJet.SmartMetropolis.service.SportService;

@Controller
public class SportController {

    @Autowired
    private SportService sportService;

    @GetMapping("/sport_view")
    public String showSportView(Model model) {
        List<Sport> sports = sportService.getAllSports();
        model.addAttribute("sports", sports);
        return "sport_view"; // Ensure this is the correct name of your HTML file
    }

    @GetMapping("/add_sport")
    public String addSportForm(Model model) {
        model.addAttribute("sport", new Sport());
        return "add_sport"; // Ensure this is the correct name of your HTML file
    }

    @PostMapping("/add_sport")
    public String addSport(@ModelAttribute Sport sport, @RequestParam("image") MultipartFile image, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_sport"; // Return to form with errors
        }
        sportService.saveSport(sport, image);
        return "redirect:/sport_view"; // Redirect to list view
    }

    @GetMapping("/edit_sport")
    public String editSportForm(@RequestParam("id") Long id, Model model) {
        Sport sport = sportService.getSportById(id);
        if (sport != null) {
            model.addAttribute("sport", sport);
            return "edit_sport"; // Ensure this matches the name of your HTML file
        } else {
            return "redirect:/sport_view"; // Redirect if sport not found
        }
    }

    @PostMapping("/update_sport")
    public String updateSport(@ModelAttribute Sport sport, @RequestParam("image") MultipartFile image) {
        sportService.saveSport(sport, image);
        return "redirect:/sport_view"; // Redirect to a page that lists all sports or shows a confirmation
    }
    
    @PostMapping("/delete_sport")
    public String deleteSport(@RequestParam("id") Long id) {
        System.out.println("Deleting sport with ID: " + id); // Add logging
        sportService.deleteSport(id);
        return "redirect:/sport_view";
    }

    @GetMapping("/sports")
    public String viewSportsPage(Model model) {
        List<Sport> sportsList = sportService.getAllSports(); // Fetch the list of sports
        model.addAttribute("sportsList", sportsList);
        return "schools"; // Ensure this is the correct name of your HTML file
    }

    @GetMapping("/get_sports")
    @ResponseBody
    public List<Sport> getAllSports() {
        return sportService.getAllSports(); // Fetch and return the list of sports
    }
    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "admin-dashboard"; // This will load the admin-dashboard.html from the templates directory
    }

}
