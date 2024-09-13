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

import com.StackJet.SmartMetropolis.entity.Hotel;
import com.StackJet.SmartMetropolis.service.HotelService;

@Controller
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotel_view")
    public String showHotelView(Model model) {
        List<Hotel> hotels = hotelService.getAllHotels();
        model.addAttribute("hotels", hotels);
        return "hotel_view"; // Ensure this is the correct name of your HTML file
    }

    @GetMapping("/add_hotel")
    public String addHotelForm(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "add_hotel"; // Ensure this is the correct name of your HTML file
    }

    @PostMapping("/add_hotel")
    public String addHotel(@ModelAttribute Hotel hotel, @RequestParam("image") MultipartFile image, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_hotel"; // Return to form with errors
        }
        hotelService.saveHotel(hotel, image);
        return "redirect:/hotel_view"; // Redirect to list view
    }

    @GetMapping("/edit_hotel")
    public String editHotelForm(@RequestParam("id") Long id, Model model) {
        Hotel hotel = hotelService.getHotelById(id);
        if (hotel != null) {
            model.addAttribute("hotel", hotel);
            return "edit_hotel"; // Ensure this matches the name of your HTML file
        } else {
            return "redirect:/hotel_view"; // Redirect if hotel not found
        }
    }

    @PostMapping("/update_hotel")
    public String updateHotel(@ModelAttribute Hotel hotel, @RequestParam("image") MultipartFile image) {
        hotelService.saveHotel(hotel, image);
        return "redirect:/hotel_view"; // Redirect to a page that lists all hotels or shows a confirmation
    }
    
    @PostMapping("/delete_hotel")
    public String deleteHotel(@RequestParam("id") Long id) {
        hotelService.deleteHotel(id);
        return "redirect:/hotel_view";
    }

    @GetMapping("/get_hotels")
    @ResponseBody
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels(); // Fetch and return the list of hotels
    }
    @GetMapping("/hotel")
    public String viewHotelsPage(Model model) {
        List<Hotel> hotelsList = hotelService.getAllHotels();
        model.addAttribute("hotels", hotelsList);
        return "hotel";
    }


}
