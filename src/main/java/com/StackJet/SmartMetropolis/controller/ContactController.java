package com.StackJet.SmartMetropolis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.StackJet.SmartMetropolis.entity.Contact;
import com.StackJet.SmartMetropolis.repository.ContactRepository;
import com.StackJet.SmartMetropolis.service.EmailService;

import jakarta.validation.Valid;

@Controller
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContactForm(@Valid @ModelAttribute("contact") Contact contact, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "contact";
        }

        try {
            contactRepository.save(contact);

            // Send email notification
            emailService.sendSimpleMessage("anulekshmi2701@gmail.com", "New Contact Form Submission",
                    "Name: " + contact.getName() + "\nEmail: " + contact.getEmail() + "\nSubject: " + contact.getSubject() + "\nMessage: " + contact.getMessage());

            model.addAttribute("successMessage", "Your message has been sent successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "There was an error sending your message. Please try again later.");
            return "contact";
        }

        return "contact";
    }

    @GetMapping("/contact/view")
    public String viewContactFormSubmissions(Model model) {
        model.addAttribute("contacts", contactRepository.findAll());
        return "contact_view";
    }
}
