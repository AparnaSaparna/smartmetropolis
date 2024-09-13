package com.StackJet.SmartMetropolis.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.StackJet.SmartMetropolis.entity.University;
import com.StackJet.SmartMetropolis.repository.UniversityRepository;

@Service
public class UniversityService {

    @Autowired
    private UniversityRepository universityRepository;

    private final String UPLOADED_FOLDER = "uploads/";

    public List<University> getAllUniversities() {
        return universityRepository.findAll();
    }

    public University getUniversityById(Long id) {
        return universityRepository.findById(id).orElse(null);
    }

    public void saveUniversity(University university, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                // Ensure the directory exists
                File uploadDir = new File(UPLOADED_FOLDER);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Save the file locally
                String fileName = image.getOriginalFilename();
                Path path = Paths.get(UPLOADED_FOLDER + fileName);
                Files.write(path, image.getBytes());

                // Log the image path for debugging
                System.out.println("Image saved at: " + path.toString());

                // Save the file path or filename to the University entity
                university.setImageUrl(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        universityRepository.save(university);
    }

    public void deleteUniversity(Long id) {
        University university = universityRepository.findById(id).orElse(null);
        if (university != null) {
            // Delete the image file if it exists
            String imagePath = UPLOADED_FOLDER + university.getImageUrl();
            try {
                Files.deleteIfExists(Paths.get(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            universityRepository.deleteById(id);
        }
    }
}
