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

import com.StackJet.SmartMetropolis.entity.TouristPlace;
import com.StackJet.SmartMetropolis.service.TouristPlaceService;

@Controller
public class TouristPlaceController {

    @Autowired
    private TouristPlaceService touristPlaceService;

    @GetMapping("/place_view")
    public String showPlaceView(Model model) {
        List<TouristPlace> places = touristPlaceService.getAllPlaces();
        model.addAttribute("places", places);
        return "place_view"; // Ensure this is the correct HTML file name
    }
    @GetMapping("/add_place")
    public String addPlaceForm(Model model) {
        model.addAttribute("place", new TouristPlace());
        return "add_place"; // Ensure this matches the name of your HTML file
    }

    @PostMapping("/add_place")
    public String addPlace(@ModelAttribute TouristPlace place, @RequestParam("image") MultipartFile image, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_place"; // Return to form with errors
        }
        touristPlaceService.savePlace(place, image);
        return "redirect:/place_view"; // Redirect to list view
    }

    @GetMapping("/edit_place")
    public String editPlaceForm(@RequestParam("id") Long id, Model model) {
        TouristPlace place = touristPlaceService.getPlaceById(id);
        if (place != null) {
            model.addAttribute("place", place);
            return "edit_place"; // Ensure this matches the name of your HTML file
        } else {
            return "redirect:/place_view"; // Redirect if place not found
        }
    }

    @PostMapping("/update_place")
    public String updatePlace(@ModelAttribute TouristPlace place, @RequestParam("image") MultipartFile image) {
        touristPlaceService.savePlace(place, image);
        return "redirect:/place_view"; // Redirect to a page that lists all places or shows a confirmation
    }

    @PostMapping("/delete_place")
    public String deletePlace(@RequestParam("id") Long id) {
        touristPlaceService.deletePlace(id);
        return "redirect:/place_view";
    }
    @GetMapping("/touristsport")
    public String viewTouristSpots(Model model) {
        model.addAttribute("places", touristPlaceService.getAllPlaces());
        return "touristsport"; // Ensure this matches your template file name
    }
}
