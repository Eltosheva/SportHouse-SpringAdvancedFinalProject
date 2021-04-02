package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.bindingModels.CoachRegisterBindingModel;
import com.eltosheva.sporthouse.models.service.CoachServiceModel;
import com.eltosheva.sporthouse.models.service.UserServiceModel;
import com.eltosheva.sporthouse.services.SportService;
import com.eltosheva.sporthouse.services.UserService;
import org.modelmapper.ModelMapper;
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

    public CoachController(SportService sportService, UserService userService, ModelMapper modelMapper) {
        this.sportService = sportService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(path="/schedules", method = RequestMethod.GET)
    public String manageCoachSchedule() { return "/coach/schedule";}

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
    public String coachProfilePage(Model model, HttpSession httpSession) {
        if (!model.containsAttribute("coachRegisterBindingModel")) {
            model.addAttribute("coachRegisterBindingModel", new CoachRegisterBindingModel());
            model.addAttribute("isWrongConfirmPassword", false);
        }
        if (!model.containsAttribute("userEmailExist")) {
            model.addAttribute("userEmailExist", false);
        }
        model.addAttribute("sports", sportService.getSports());
        return "coach/register";
    }

    @PostMapping("/profile")
    public String coachProfileCorrection(@Valid @ModelAttribute CoachRegisterBindingModel coachRegisterBindingModel,
                                         BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("coachRegisterBindingModel",
                    coachRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.coachRegisterBindingModel",
                    bindingResult);
            redirectAttributes.addFlashAttribute("isWrongConfirmPassword",
                    coachRegisterBindingModel.getPassword().equals(coachRegisterBindingModel.getConfirmPassword()));
            return "redirect:/user/profile";
        }
        if (!coachRegisterBindingModel.getPassword().equals(coachRegisterBindingModel
                .getConfirmPassword())) {

        }
        if (userService.findByEmail(coachRegisterBindingModel.getEmail()) != null) {
            UserServiceModel userServiceModel = userService.findById(coachRegisterBindingModel.getId());
            modelMapper.map(coachRegisterBindingModel, userServiceModel);
            userServiceModel.setActive(true);
        }
        return "redirect:/user/profile";
    }
}
