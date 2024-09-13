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

import com.StackJet.SmartMetropolis.entity.Sport;
import com.StackJet.SmartMetropolis.repository.SportRepository;

@Service
public class SportService {

    @Autowired
    private SportRepository sportRepository;

    private final String UPLOADED_FOLDER = "uploads/";

    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    public Sport getSportById(Long id) {
        return sportRepository.findById(id).orElse(null);
    }

    public void saveSport(Sport sport, MultipartFile image) {
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

                // Save the file path or filename to the Sport entity
                sport.setImagePath(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception, e.g., log it or show an error message
            }
        }
        // Save the sport entity (with or without image path)
        sportRepository.save(sport);
    }
    
    
    public void deleteSport(Long id) {
        Sport sport = sportRepository.findById(id).orElse(null);
        if (sport != null) {
            // Delete the image file if it exists
            String imagePath = UPLOADED_FOLDER + sport.getImagePath();
            try {
                Files.deleteIfExists(Paths.get(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            sportRepository.deleteById(id);
        }
    }

	public List<Sport> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
    
    
}
