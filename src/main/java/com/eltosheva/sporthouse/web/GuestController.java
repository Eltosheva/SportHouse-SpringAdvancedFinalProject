package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.exceptions.UnauthorizedException;
import com.eltosheva.sporthouse.models.service.ProductServiceModel;
import com.eltosheva.sporthouse.services.PlaceService;
import com.eltosheva.sporthouse.services.ProductService;
import com.eltosheva.sporthouse.services.UserService;
import com.eltosheva.sporthouse.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
public class GuestController {

    private final PlaceService placeService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "invalid-session", defaultValue = "false") boolean invalidSession,
                        Model model) {
        if (invalidSession) {
            model.addAttribute("invalidSession", "Session expired please re-login.");
        }
        return "login";
    }

    @PostMapping("/loginError")
    public String failedToLogin(@ModelAttribute(AppConstants.SPRING_SECURITY_LOGIN_EMAIL_KEY) String email,
                                RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("bad_credentials", true);
        redirectAttributes.addFlashAttribute("email", email);
        return "redirect:/login";
    }

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("places", placeService.getPlaces());
        model.addAttribute("productsSubs", productService.getTopThree());
        return "index";
    }

    @GetMapping("/index")
    public String home() {
        if (Math.random() < 0.5) {
            throw new UnauthorizedException("The user is not logged in");
        }
        return "home";
    }

    @GetMapping("/halls")
    public String places(Model model) {
        model.addAttribute("places", placeService.getPlaces());
        return "places";
    }

    @GetMapping("/store")
    public String store() {
        return "store";
    }

    @GetMapping("/sports")
    public String sports() {
        return "sports";
    }

    @GetMapping("/team")
    public String teamPage(Model model) {
        model.addAttribute("trainers", userService.getAllCoaches());
        return "team";
    }
}
