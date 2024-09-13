package com.StackJet.SmartMetropolis.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    @GetMapping("/admin/login")
    public String showAdminLogin() {
        return "admin-login"; // This will load the admin-login.html from the templates directory
    }

    @PostMapping("/admin/login")
    public ModelAndView adminLogin(@RequestParam("username") String username,
                                   @RequestParam("password") String password) {
        if ("smartmetropolis".equals(username) && "admin".equals(password)) {
            return new ModelAndView("admin-dashboard"); // This will load the admin-dashboard.html from the templates directory
        } else {
            ModelAndView modelAndView = new ModelAndView("admin-login");
            modelAndView.addObject("error", "Invalid username or password.");
            return modelAndView;
        }
    }
  
}
