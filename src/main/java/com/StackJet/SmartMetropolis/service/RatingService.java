package com.StackJet.SmartMetropolis.service;

import com.StackJet.SmartMetropolis.entity.Rating;
import com.StackJet.SmartMetropolis.entity.User;
import com.StackJet.SmartMetropolis.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    public void saveRating(String userName, int stars) {
        Rating rating = new Rating();
        rating.setUserName(userName);
        rating.setStars(stars);
        ratingRepository.save(rating);

        // Fetch the user's email
        User user = userService.findUserByEmail(userName);
        String userEmail = user != null ? user.getEmail() : "default@example.com";

        // Send confirmation email
        String subject = "Rating Confirmation";
        String body = "Thank you for your rating, " + userName + ". You rated us " + stars + " stars.";
        emailService.sendSimpleMessage(userEmail, subject, body);
    }
}
