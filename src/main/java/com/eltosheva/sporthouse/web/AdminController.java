package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.bindingModels.ProductBindingModel;
import com.eltosheva.sporthouse.bindingModels.PlaceBindingModel;
import com.eltosheva.sporthouse.bindingModels.SportBindingModel;
import com.eltosheva.sporthouse.bindingModels.SubscriptionBindingModel;
import com.eltosheva.sporthouse.models.service.ProductServiceModel;
import com.eltosheva.sporthouse.models.service.PlaceServiceModel;
import com.eltosheva.sporthouse.models.service.SportServiceModel;
import com.eltosheva.sporthouse.models.service.SubscriptionServiceModel;
import com.eltosheva.sporthouse.services.ProductService;
import com.eltosheva.sporthouse.services.PlaceService;
import com.eltosheva.sporthouse.services.SportService;
import com.eltosheva.sporthouse.services.SubscriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    public AdminController(ModelMapper modelMapper, ProductService productService, SportService sportService, PlaceService placeService, SubscriptionService subscriptionService) {
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.sportService = sportService;
        this.placeService = placeService;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/users")
    public String getAppUsers(Model model) {
        model.addAttribute("users", "");
        return "admin/users";
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
        sportService.addNewSport(modelMapper.map(sportBindingModel, SportServiceModel.class));
        redirectAttributes.addFlashAttribute("isSportSaveSuccessfully", true);
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
        placeService.addNewSportHall(modelMapper.map(placeBindingModel, PlaceServiceModel.class));
        redirectAttributes.addFlashAttribute("isPlaceSavedSuccessfully", true);
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

    @RequestMapping(path = "/subscriptions", method = RequestMethod.GET)
    public String manageSubscriptionsPage(Model model) {
        if (!model.containsAttribute("subscriptionBindingModel")) {
            model.addAttribute("subscriptionBindingModel", new SubscriptionBindingModel());
        }
        if (!model.containsAttribute("isSubscriptionSavedSuccessfully")) {
            model.addAttribute("isSubscriptionSavedSuccessfully", false);
        }
        model.addAttribute("subscriptions", subscriptionService.getAllSubscriptions());
        return "admin/sport_plans";
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
        subscriptionService.addNewSubscription(modelMapper.map(subscriptionBindingModel, SubscriptionServiceModel.class));
        redirectAttributes.addFlashAttribute("isSubscriptionSavedSuccessfully", true);
        return "redirect:/admin/subscriptions";
    }

    @RequestMapping(path = "/tasks", method = RequestMethod.GET)
    public String manageTasksPage() {
        return "admin/tasks";
    }
}
