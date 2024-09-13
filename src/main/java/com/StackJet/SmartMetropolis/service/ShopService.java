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

import com.StackJet.SmartMetropolis.entity.Shop;
import com.StackJet.SmartMetropolis.repository.ShopRepository;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    private final String UPLOADED_FOLDER = "uploads/";

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    public Shop getShopById(Long id) {
        return shopRepository.findById(id).orElse(null);
    }

    public void saveShop(Shop shop, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                File uploadDir = new File(UPLOADED_FOLDER);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String fileName = image.getOriginalFilename();
                Path path = Paths.get(UPLOADED_FOLDER + fileName);
                Files.write(path, image.getBytes());
                shop.setImagePath(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        shopRepository.save(shop);
    }

    public void deleteShop(Long id) {
        Shop shop = shopRepository.findById(id).orElse(null);
        if (shop != null) {
            String imagePath = UPLOADED_FOLDER + shop.getImagePath();
            try {
                Files.deleteIfExists(Paths.get(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            shopRepository.deleteById(id);
        }
    }
}
