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

import com.StackJet.SmartMetropolis.entity.Hotel;
import com.StackJet.SmartMetropolis.repository.HotelRepository;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    private final String UPLOADED_FOLDER = "uploads/";

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public void saveHotel(Hotel hotel, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                File uploadDir = new File(UPLOADED_FOLDER);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String fileName = image.getOriginalFilename();
                Path path = Paths.get(UPLOADED_FOLDER + fileName);
                Files.write(path, image.getBytes());

                hotel.setImagePath(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        hotelRepository.save(hotel);
    }

    public void deleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElse(null);
        if (hotel != null) {
            String imagePath = UPLOADED_FOLDER + hotel.getImagePath();
            try {
                Files.deleteIfExists(Paths.get(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            hotelRepository.deleteById(id);
        }
    }
}
