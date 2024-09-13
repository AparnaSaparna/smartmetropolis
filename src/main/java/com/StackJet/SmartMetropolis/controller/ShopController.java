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

import com.StackJet.SmartMetropolis.entity.Shop;
import com.StackJet.SmartMetropolis.service.ShopService;

@Controller
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/shop_view")
    public String showShopView(Model model) {
        List<Shop> shops = shopService.getAllShops();
        model.addAttribute("shops", shops);
        return "shop_view"; // Ensure this is the correct name of your HTML file
    }

    @GetMapping("/add_shop")
    public String addShopForm(Model model) {
        model.addAttribute("shop", new Shop());
        return "add_shop"; // Ensure this is the correct name of your HTML file
    }

    @PostMapping("/add_shop")
    public String addShop(@ModelAttribute Shop shop, @RequestParam("image") MultipartFile image, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_shop"; // Return to form with errors
        }
        shopService.saveShop(shop, image);
        return "redirect:/shop_view"; // Redirect to list view
    }

    @GetMapping("/edit_shop")
    public String editShopForm(@RequestParam("id") Long id, Model model) {
        Shop shop = shopService.getShopById(id);
        if (shop != null) {
            model.addAttribute("shop", shop);
            return "edit_shop"; // Ensure this matches the name of your HTML file
        } else {
            return "redirect:/shop_view"; // Redirect if shop not found
        }
    }

    @PostMapping("/update_shop")
    public String updateShop(@ModelAttribute Shop shop, @RequestParam("image") MultipartFile image) {
        shopService.saveShop(shop, image);
        return "redirect:/shop_view"; // Redirect to a page that lists all shops or shows a confirmation
    }

    @PostMapping("/delete_shop")
    public String deleteShop(@RequestParam("id") Long id) {
        shopService.deleteShop(id);
        return "redirect:/shop_view";
    }

    @GetMapping("/get_shops")
    @ResponseBody
    public List<Shop> getAllShops() {
        return shopService.getAllShops();
    }
    @GetMapping("/ShoppingCenters")
    public String viewShoppingCentersPage(Model model) {
        List<Shop> shopsList = shopService.getAllShops(); // Fetch the list of shops
        model.addAttribute("shops", shopsList);
        return "ShoppingCenters"; // Ensure this is the correct name of your HTML file
    }

}
