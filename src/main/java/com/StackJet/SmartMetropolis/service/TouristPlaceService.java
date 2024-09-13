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

import com.StackJet.SmartMetropolis.entity.TouristPlace;
import com.StackJet.SmartMetropolis.repository.TouristPlaceRepository;

@Service
public class TouristPlaceService {

    @Autowired
    private TouristPlaceRepository touristPlaceRepository;

    private final String UPLOADED_FOLDER = "uploads/";

    public List<TouristPlace> getAllPlaces() {
        return touristPlaceRepository.findAll();
    }

    public TouristPlace getPlaceById(Long id) {
        return touristPlaceRepository.findById(id).orElse(null);
    }

    public void savePlace(TouristPlace place, MultipartFile image) {
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

                // Save the file path or filename to the TouristPlace entity
                place.setImageUrl(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        touristPlaceRepository.save(place);
    }

    public void deletePlace(Long id) {
        TouristPlace place = touristPlaceRepository.findById(id).orElse(null);
        if (place != null) {
            // Delete the image file if it exists
            String imagePath = UPLOADED_FOLDER + place.getImageUrl();
            try {
                Files.deleteIfExists(Paths.get(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            touristPlaceRepository.deleteById(id);
        }
    }
}
