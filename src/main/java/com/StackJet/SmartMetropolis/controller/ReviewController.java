package com.StackJet.SmartMetropolis.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.StackJet.SmartMetropolis.entity.Review;
import com.StackJet.SmartMetropolis.entity.User;
import com.StackJet.SmartMetropolis.service.ReviewService;
import com.StackJet.SmartMetropolis.service.UserService;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    // Method to fetch the logged-in user's name
    private String getLoggedInUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Get the logged-in user's email
        User user = userService.findUserByEmail(email); // Fetch user by email
        return user.getName(); // Return the user's name
    }

    @PostMapping("/submitReview")
    public String submitReview(@RequestParam("name") String name,
                               @RequestParam("feedback") String feedback,
                               @RequestParam("file") MultipartFile file,
                               Model model) {
        Review review = new Review();
        review.setName(name);
        review.setFeedback(feedback);

        // Handle file storage and set the image URL or path
        reviewService.saveReview(review, file);

        // Redirect to the home page to display all reviews
        return "redirect:/"; // Redirect to index.html
    }

    @PostMapping("/delete_review")
    public String deleteReview(@RequestParam("id") Long id) {
        System.out.println("Deleting review with ID: " + id); // Log the deletion
        reviewService.deleteReview(id);
        return "redirect:/review_view"; // Redirect to review_view.html
    }

    @GetMapping("/review_view")
    public String viewReview(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        System.out.println("Reviews: " + reviews); // Log the list of reviews
        model.addAttribute("reviews", reviews);
        return "review_view"; // Return the view name
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "index"; // This should match index.html in 
    }
}
