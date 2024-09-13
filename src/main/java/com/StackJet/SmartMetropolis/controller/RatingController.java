package com.StackJet.SmartMetropolis.controller;

import com.StackJet.SmartMetropolis.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/submit")
    public String submitRating(@RequestParam int stars) {
        try {
            // Get the currently authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            
            // Save the rating
            ratingService.saveRating(currentUserName, stars);

            // Redirect to the root context which serves static files like index.html
            return "redirect:/"; // Serves src/main/resources/static/index.html
        } catch (Exception e) {
            e.printStackTrace();
            // Redirect to the root context if an error occurs
            return "redirect:/";
        }
    }
}
