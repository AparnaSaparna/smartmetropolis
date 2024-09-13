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

import com.StackJet.SmartMetropolis.entity.Library;
import com.StackJet.SmartMetropolis.repository.LibraryRepository;

@Service
public class LibraryService {

    @Autowired
    private LibraryRepository libraryRepository;

    private final String UPLOADED_FOLDER = "uploads/";

    public List<Library> getAllLibraries() {
        return libraryRepository.findAll();
    }

    public Library getLibraryById(Long id) {
        return libraryRepository.findById(id).orElse(null);
    }

    public void saveLibrary(Library library, MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                File uploadDir = new File(UPLOADED_FOLDER);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String fileName = image.getOriginalFilename();
                Path path = Paths.get(UPLOADED_FOLDER + fileName);
                Files.write(path, image.getBytes());

                library.setImagePath(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        libraryRepository.save(library);
    }

    public void deleteLibrary(Long id) {
        Library library = libraryRepository.findById(id).orElse(null);
        if (library != null) {
            String imagePath = UPLOADED_FOLDER + library.getImagePath();
            try {
                Files.deleteIfExists(Paths.get(imagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            libraryRepository.deleteById(id);
        }
    }
}
