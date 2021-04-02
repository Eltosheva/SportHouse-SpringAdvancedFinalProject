package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.bindingModels.SportsmanRegisterBindingModel;
import com.eltosheva.sporthouse.models.service.SportsmanServiceModel;
import com.eltosheva.sporthouse.models.service.UserOrdersServiceModel;
import com.eltosheva.sporthouse.models.service.UserServiceModel;
import com.eltosheva.sporthouse.services.UserOrderHistoryService;
import com.eltosheva.sporthouse.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserOrderHistoryService userOrderHistoryService;

    public UserController(UserService userService, ModelMapper modelMapper,
                          UserOrderHistoryService userOrderHistoryService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userOrderHistoryService = userOrderHistoryService;
    }

    @GetMapping("/register")
    public String userRegisterPage(Model model, HttpSession httpSession) {
        if (!model.containsAttribute("sportsmanRegisterBindingModel")){
            model.addAttribute("sportsmanRegisterBindingModel", new SportsmanRegisterBindingModel());
            model.addAttribute("isWrongConfirmPassword", false);
        }
        if (!model.containsAttribute("errMessage")) {
            model.addAttribute("errMessage", "");
        }
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute SportsmanRegisterBindingModel sportsmanRegisterBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || !sportsmanRegisterBindingModel.getPassword().equals(sportsmanRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("sportsmanRegisterBindingModel",
                    sportsmanRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sportsmanRegisterBindingModel",
                    bindingResult);
            redirectAttributes.addFlashAttribute("isWrongConfirmPassword",
                    sportsmanRegisterBindingModel.getPassword().equals(sportsmanRegisterBindingModel.getConfirmPassword()));
            return "redirect:/user/register";
        }
        if (userService.findByEmail(sportsmanRegisterBindingModel.getEmail()) != null) {
            redirectAttributes.addFlashAttribute("errMessage", "This email address is already used for registration.");
            redirectAttributes.addFlashAttribute("sportsmanRegisterBindingModel",
                    sportsmanRegisterBindingModel);
            return "redirect:/user/register";
        }
        userService.createUser(modelMapper.map(sportsmanRegisterBindingModel, SportsmanServiceModel.class));
        return "redirect:/login";
    }

    @GetMapping("/subscriptions")
    public String userSubscriptionsPage() {
        return "user/subscription";
    }

    @GetMapping("/schedules")
    public String userSchedulesPage() {
        return "user/schedule";
    }

    @PostMapping("/order")
    public String createOrder() {
        userOrderHistoryService.createOrder();
        return "redirect:/store";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("user", userService.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()));
        return "profile";
    }

    @PostMapping("/profile")
    public String userProfileCorrection(@Valid @ModelAttribute SportsmanRegisterBindingModel sportsmanRegisterBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("sportsmanRegisterBindingModel",
                    sportsmanRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sportsmanRegisterBindingModel",
                    bindingResult);
            redirectAttributes.addFlashAttribute("isWrongConfirmPassword",
                    sportsmanRegisterBindingModel.getPassword().equals(sportsmanRegisterBindingModel.getConfirmPassword()));
            return "redirect:/user/profile";
        }
        
//      !userProfileBindingModel.getPassword().equals(userProfileBindingModel.getConfirmPassword())
        if (userService.findByEmail(sportsmanRegisterBindingModel.getEmail()) != null) {
            UserServiceModel userServiceModel = userService.findById(sportsmanRegisterBindingModel.getId());
            modelMapper.map(sportsmanRegisterBindingModel, userServiceModel);
            userServiceModel.setActive(true);
        }
        return "redirect:/user/profile";
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }

    @GetMapping("/orders")
    public String userOrdersPage(Model model) {
        model.addAttribute("orders", userOrderHistoryService.findAllUserOrders());
        return "/user/orders";
    }

}
