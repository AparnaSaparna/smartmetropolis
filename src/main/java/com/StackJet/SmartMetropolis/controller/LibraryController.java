package com.StackJet.SmartMetropolis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.StackJet.SmartMetropolis.entity.Library;
import com.StackJet.SmartMetropolis.service.LibraryService;

@Controller
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/library_view")
    public String showLibraryView(Model model) {
        List<Library> libraries = libraryService.getAllLibraries();
        model.addAttribute("libraries", libraries);
        return "library_view";
    }

    @GetMapping("/add_library")
    public String addLibraryForm(Model model) {
        model.addAttribute("library", new Library());
        return "add_library";
    }

    @PostMapping("/add_library")
    public String addLibrary(@ModelAttribute Library library, @RequestParam("image") MultipartFile image, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_library";
        }
        libraryService.saveLibrary(library, image);
        return "redirect:/library_view";
    }

    @GetMapping("/edit_library")
    public String editLibraryForm(@RequestParam("id") Long id, Model model) {
        Library library = libraryService.getLibraryById(id);
        if (library != null) {
            model.addAttribute("library", library);
            return "edit_library";
        } else {
            return "redirect:/library_view";
        }
    }

    @PostMapping("/update_library")
    public String updateLibrary(@ModelAttribute Library library, @RequestParam("image") MultipartFile image) {
        libraryService.saveLibrary(library, image);
        return "redirect:/library_view";
    }

    @PostMapping("/delete_library")
    public String deleteLibrary(@RequestParam("id") Long id) {
        libraryService.deleteLibrary(id);
        return "redirect:/library_view";
    }

    @GetMapping("/libraries")
    public String viewLibrariesPage(Model model) {
        List<Library> libraries = libraryService.getAllLibraries();
        model.addAttribute("librariesList", libraries);
        return "library";
    }

    @GetMapping("/get_libraries")
    @ResponseBody
    public List<Library> getAllLibraries() {
        return libraryService.getAllLibraries();
    }
    @GetMapping("/library")
    public String viewLibraries(Model model) {
        List<Library> libraries = libraryService.getAllLibraries();
        model.addAttribute("libraries", libraries);
        return "library"; // Ensure this is the correct name of your HTML file
    }


}
