package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.models.bindingModels.CoachProfileBindingModel;
import com.eltosheva.sporthouse.models.bindingModels.CoachRegisterBindingModel;
import com.eltosheva.sporthouse.models.bindingModels.ProfileBindingModel;
import com.eltosheva.sporthouse.models.service.CoachServiceModel;
import com.eltosheva.sporthouse.models.service.UserServiceModel;
import com.eltosheva.sporthouse.repositories.UserRepository;
import com.eltosheva.sporthouse.services.SportService;
import com.eltosheva.sporthouse.services.UserService;
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

@Controller
@RequestMapping(path = "/coach")
public class CoachController {

    private final SportService sportService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public CoachController(SportService sportService, UserService userService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.sportService = sportService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @RequestMapping(path="/schedules", method = RequestMethod.GET)
    public String manageCoachSchedule() { return "coach/schedule";}

    @GetMapping("/register")
    public String coachRegisterPage(Model model, HttpSession httpSession) {
        if (!model.containsAttribute("coachRegisterBindingModel")) {
            model.addAttribute("coachRegisterBindingModel", new CoachRegisterBindingModel());
            model.addAttribute("isWrongConfirmPassword", false);
        }

        if (!model.containsAttribute("errMessage")) {
            model.addAttribute("errMessage", "");
        }

        model.addAttribute("sports", sportService.getSports());
        return "coach/register";
    }

    @PostMapping("/register")
    public String registerCoach(@Valid @ModelAttribute CoachRegisterBindingModel coachRegisterBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() ||
                !coachRegisterBindingModel.getPassword().equals(coachRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("coachRegisterBindingModel", coachRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.coachRegisterBindingModel",
                    bindingResult);
            redirectAttributes.addFlashAttribute("isWrongConfirmPassword",
                    coachRegisterBindingModel.getPassword().equals(coachRegisterBindingModel.getConfirmPassword()));
            return "redirect:/coach/register";
        }
        if (userService.findByEmail(coachRegisterBindingModel.getEmail()) != null) {
            redirectAttributes.addFlashAttribute("errMessage", "This email address is already used for registration.");
            redirectAttributes.addFlashAttribute("coachRegisterBindingModel", coachRegisterBindingModel);
            return "redirect:/coach/register";
        }

        userService.createUser(modelMapper.map(coachRegisterBindingModel, CoachServiceModel.class));
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String coachProfilePage(Model model) {
        CoachServiceModel coach = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .map(user ->  modelMapper.map(user, CoachServiceModel.class))
                .orElse(null);

        model.addAttribute("user", coach);
        model.addAttribute("sports", sportService.getSports());

        return "profile";
    }

    @PostMapping("/profile")
    public String coachProfileCorrection(@Valid @ModelAttribute CoachProfileBindingModel coachProfileBindingModel,
                                         BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("coachProfileBindingModel",
                    coachProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.coachProfileBindingModel",
                    bindingResult);
            return "redirect:/coach/profile";
        }

        String validation = validateUserData(coachProfileBindingModel);
        if(!"".equals(validation)) {
            redirectAttributes.addFlashAttribute("coachProfileBindingModel",
                    coachProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.coachProfileBindingModel",
                    bindingResult);
            redirectAttributes.addFlashAttribute("errorMessage", validation);
            return "redirect:/coach/profile";
        }

        CoachServiceModel coachServiceModel = modelMapper.map(coachProfileBindingModel, CoachServiceModel.class);
        if(!"".equals(coachProfileBindingModel.getNewPassword())) {
            coachServiceModel.setPassword(passwordEncoder.encode(coachProfileBindingModel.getNewPassword()));
        } else {
            coachServiceModel.setPassword("");
        }

        userService.editUser(coachServiceModel);

        redirectAttributes.addFlashAttribute("success", "User data have been updated successfully.");

        return "redirect:/coach/profile";
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
}
