package com.StackJet.SmartMetropolis.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.StackJet.SmartMetropolis.entity.Review;
import com.StackJet.SmartMetropolis.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    private final String UPLOADED_FOLDER = "uploads/";

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Overloaded method to handle review without a file
    public void saveReview(Review review) {
        saveReview(review, null);
    }

    public void saveReview(Review review, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                // Ensure the directory exists
                File uploadDir = new File(UPLOADED_FOLDER);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Create a unique file name
                String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path path = Paths.get(UPLOADED_FOLDER + uniqueFileName);
                Files.write(path, file.getBytes());

                // Save the file path or filename to the Review entity
                review.setFilePath(uniqueFileName);
            } catch (IOException e) {
                e.printStackTrace();
                // Consider logging or rethrowing the exception
            }
        }
        // Save the review entity (with or without file path)
        reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review != null) {
            // Delete the file if it exists
            String filePath = review.getFilePath();
            if (filePath != null && !filePath.isEmpty()) {
                Path path = Paths.get(UPLOADED_FOLDER + filePath);
                if (Files.exists(path)) {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Consider logging or rethrowing the exception
                    }
                }
            }
            reviewRepository.deleteById(id);
        }
    }
}
