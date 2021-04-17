package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.models.bindingModels.ProfileBindingModel;
import com.eltosheva.sporthouse.models.bindingModels.SportsmanRegisterBindingModel;
import com.eltosheva.sporthouse.models.bindingModels.UserProfileBindingModel;
import com.eltosheva.sporthouse.models.service.SportsmanServiceModel;
import com.eltosheva.sporthouse.models.service.UserServiceModel;
import com.eltosheva.sporthouse.services.UserOrderHistoryService;
import com.eltosheva.sporthouse.services.UserScheduleService;
import com.eltosheva.sporthouse.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequestMapping("/user")
@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserOrderHistoryService userOrderHistoryService;
    private final PasswordEncoder passwordEncoder;
    private final UserScheduleService userScheduleService;

    @GetMapping("/register")
    public String userRegisterPage(Model model, HttpSession httpSession) {
        if (!model.containsAttribute("sportsmanRegisterBindingModel")){
            model.addAttribute("sportsmanRegisterBindingModel", new SportsmanRegisterBindingModel());
            model.addAttribute("isWrongConfirmPassword", false);
            model.addAttribute("isFirstTime", true);
        } else {
            model.addAttribute("isFirstTime", false);
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
        return "subscriptions";
    }

    @GetMapping("/schedules")
    public String userSchedulesPage(Model model) {
        model.addAttribute("schedulesList", userScheduleService.findAllUserSchedules());
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
    public String userProfileCorrection(@Valid @ModelAttribute UserProfileBindingModel userProfileBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userProfileBindingModel",
                    userProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileBindingModel",
                    bindingResult);
            return "redirect:/user/profile";
        }

        String validation = validateUserData(userProfileBindingModel);
        if(!"".equals(validation)) {
            redirectAttributes.addFlashAttribute("userProfileBindingModel",
                    userProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileBindingModel",
                    bindingResult);
            redirectAttributes.addFlashAttribute("errorMessage", validation);
            return "redirect:/user/profile";
        }

        UserServiceModel userServiceModel = modelMapper.map(userProfileBindingModel, UserServiceModel.class);
        if(!"".equals(userProfileBindingModel.getNewPassword())) {
            userServiceModel.setPassword(passwordEncoder.encode(userProfileBindingModel.getNewPassword()));
        } else {
            userServiceModel.setPassword("");
        }

        userService.editUser(userServiceModel);

        redirectAttributes.addFlashAttribute("success", "User data have been updated successfully.");
        return "redirect:/user/profile";
    }

    private String validateUserData(ProfileBindingModel profileBindingModel) {
        if(!profileBindingModel.getNewPassword().isEmpty()) {
            try {
                userService.findByEmailAndPassword(profileBindingModel.getEmail(), profileBindingModel.getPassword());
            } catch (IllegalArgumentException i) {
                return "Wrong email or password.";
            }

            if(!profileBindingModel.getNewPassword().equals(profileBindingModel.getConfirmPassword())) {
                return "New password and confirmation password didn't match.";
            }
        }
        return "";
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

    @GetMapping("/mySubs")
    public String userSubscriptionsPage(Model model) {
        model.addAttribute("subs", userOrderHistoryService.findAllUserSubscriptions());
        model.addAttribute("trainingCount", userService.getTrainingsCount());
        return "/user/subscriptions";
    }

    @RequestMapping(path = "/schedule/add", method = RequestMethod.POST)
    public String changePlaceStatus(@RequestParam String scheduleId) {
        userService.addTrainingToSchedule(scheduleId);
        return "redirect:/user/schedules";
    }
}