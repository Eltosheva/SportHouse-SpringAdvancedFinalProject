package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.services.PlaceService;
import com.eltosheva.sporthouse.services.SportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PlaceService placeService;
    private final SportService sportService;

    public HomeController(PlaceService placeService, SportService sportService) {
        this.placeService = placeService;
        this.sportService = sportService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        ///Евентуално да започва всичко от избиране на мястото за тренировка
        model.addAttribute("places", placeService.getPlaces());
        model.addAttribute("sports", sportService.getSports());
        return "home";
    }

    @GetMapping("/store")
    public String store(Model model) {
        model.addAttribute("sports", sportService.getSports());
        return "store";
    }

    @GetMapping("/help")
    public String applicationHelpPage() {
        return "help";
    }
}