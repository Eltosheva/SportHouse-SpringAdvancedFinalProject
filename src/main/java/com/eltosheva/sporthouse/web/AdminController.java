package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.jobs.SchedulerService;
import com.eltosheva.sporthouse.models.bindingModels.*;
import com.eltosheva.sporthouse.models.service.*;
import com.eltosheva.sporthouse.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    private final ModelMapper modelMapper;
    private final ProductService productService;
    private final SportService sportService;
    private final PlaceService placeService;
    private final SubscriptionService subscriptionService;
    private final SchedulerService schedulerService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(ModelMapper modelMapper, ProductService productService, SportService sportService, PlaceService placeService, SubscriptionService subscriptionService, SchedulerService schedulerService, UserService userService, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.sportService = sportService;
        this.placeService = placeService;
        this.subscriptionService = subscriptionService;
        this.schedulerService = schedulerService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(path = "/sports", method = RequestMethod.GET)
    public String manageSportsPage(Model model) {
        if (!model.containsAttribute("sportBindingModel")) {
            model.addAttribute("sportBindingModel", new SportBindingModel());
        }
        if (!model.containsAttribute("isSportSaveSuccessfully")) {
            model.addAttribute("isSportSaveSuccessfully", false);
        }
        model.addAttribute("sports", sportService.getSports());
        return "admin/sports";
    }

    @RequestMapping(path = "/sports", method = RequestMethod.POST)
    public String addNewSport(@Valid @ModelAttribute SportBindingModel sportBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("sportBindingModel", sportBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sportBindingModel",
                    bindingResult);
            return "redirect:/admin/sports";
        }
        sportService.addEditSport(modelMapper.map(sportBindingModel, SportServiceModel.class));
        redirectAttributes.addFlashAttribute("isSportSaveSuccessfully", true);
        return "redirect:/admin/sports";
    }

    @RequestMapping(path = "/sports/status", method = RequestMethod.POST)
    public String changeSportsStatus(@RequestParam String id) {
        sportService.changeStatus(id);
        return "redirect:/admin/sports";
    }

    @RequestMapping(path = "/halls", method = RequestMethod.GET)
    public String manageHallsPage(Model model) {
        if (!model.containsAttribute("placeBindingModel")) {
            model.addAttribute("placeBindingModel", new PlaceBindingModel());
        }
        if (!model.containsAttribute("isPlaceSavedSuccessfully")) {
            model.addAttribute("isPlaceSavedSuccessfully", false);
        }
        model.addAttribute("halls", placeService.getPlaces());
        return "admin/halls";
    }

    @RequestMapping(path = "/halls", method = RequestMethod.POST)
    public String addNewPlace(@Valid @ModelAttribute PlaceBindingModel placeBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("placeBindingModel", placeBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.placeBindingModel",
                    bindingResult);
            return "redirect:/admin/halls";
        }
        placeService.addEditSportHall(modelMapper.map(placeBindingModel, PlaceServiceModel.class));
        redirectAttributes.addFlashAttribute("isPlaceSavedSuccessfully", true);
        return "redirect:/admin/halls";
    }

    @RequestMapping(path = "/halls/status", method = RequestMethod.POST)
    public String changePlaceStatus(@RequestParam String id) {
        placeService.changeStatus(id);
        return "redirect:/admin/halls";
    }

    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public String manageProductsPage(Model model) {
        if (!model.containsAttribute("productBindingModel")) {
            model.addAttribute("productBindingModel", new ProductBindingModel());
        }
        if (!model.containsAttribute("isProductSavedSuccessfully")) {
            model.addAttribute("isProductSavedSuccessfully", false);
        }
        model.addAttribute("sports", sportService.getSports());
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @RequestMapping(path = "/products", method = RequestMethod.POST)
    public String addProduct(@Valid @ModelAttribute ProductBindingModel productBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productBindingModel", productBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productBindingModel",
                    bindingResult);

            return "redirect:/admin/products";
        }
        productService.addProduct(modelMapper.map(productBindingModel, ProductServiceModel.class));
        redirectAttributes.addFlashAttribute("isProductSavedSuccessfully", true);
        return "redirect:/admin/products";
    }

    @RequestMapping(path = "/products/status", method = RequestMethod.POST)
    public String changeProductsStatus(@RequestParam String id) {
        productService.changeStatus(id);
        return "redirect:/admin/products";
    }

    @RequestMapping(path = "/subscriptions", method = RequestMethod.GET)
    public String manageSubscriptionsPage(Model model) {
        if (!model.containsAttribute("subscriptionBindingModel")) {
            model.addAttribute("subscriptionBindingModel", new SubscriptionBindingModel());
        }
        if (!model.containsAttribute("isSubscriptionSavedSuccessfully")) {
            model.addAttribute("isSubscriptionSavedSuccessfully", false);
        }
        model.addAttribute("subscriptions", subscriptionService.getAllSubscriptions());
        return "admin/subscriptions";
    }

    @RequestMapping(path = "/subscriptions", method = RequestMethod.POST)
    public String addNewPlan(@Valid @ModelAttribute SubscriptionBindingModel subscriptionBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("subscriptionBindingModel", subscriptionBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.subscriptionBindingModel",
                    bindingResult);
            return "redirect:/admin/subscriptions";
        }
        subscriptionService.addEditSubscription(modelMapper.map(subscriptionBindingModel, SubscriptionServiceModel.class));
        redirectAttributes.addFlashAttribute("isSubscriptionSavedSuccessfully", true);
        return "redirect:/admin/subscriptions";
    }

    @RequestMapping(path = "/subscriptions/status", method = RequestMethod.POST)
    public String changeSubsStatus(@RequestParam String id) {
        subscriptionService.changeStatus(id);
        return "redirect:/admin/subscriptions";
    }

    @RequestMapping(path = "/tasks", method = RequestMethod.GET)
    public String manageTasksPage(Model model) {
        model.addAttribute("jobs", schedulerService.getAllJobs());
        return "admin/tasks";
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public String manageUsersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @RequestMapping(path = "/users/status", method = RequestMethod.POST)
    public String changeUserStatus(@RequestParam String email) {
        userService.changeStatus(email);
        return "redirect:/admin/users";
    }
    @GetMapping("/profile")
    public String coachProfilePage(Model model) {

        model.addAttribute("user", userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

        return "profile";
    }

    @PostMapping("/profile")
    public String coachProfileCorrection(@Valid @ModelAttribute ProfileBindingModel profileBindingModel,
                                         BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("profileBindingModel",
                    profileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.profileBindingModel",
                    bindingResult);
            return "redirect:/coach/profile";
        }

        String validation = validateUserData(profileBindingModel);
        if(!"".equals(validation)) {
            redirectAttributes.addFlashAttribute("profileBindingModel",
                    profileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.profileBindingModel",
                    bindingResult);
            redirectAttributes.addFlashAttribute("errorMessage", validation);
            return "redirect:/coach/profile";
        }

        UserServiceModel userServiceModel = modelMapper.map(profileBindingModel, UserServiceModel.class);
        if(!"".equals(profileBindingModel.getNewPassword())) {
            userServiceModel.setPassword(passwordEncoder.encode(profileBindingModel.getNewPassword()));
        } else {
            userServiceModel.setPassword("");
        }

        userService.editUser(userServiceModel);

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
