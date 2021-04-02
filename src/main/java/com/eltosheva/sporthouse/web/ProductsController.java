package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.bindingModels.ShoppingCartBindingModel;
import com.eltosheva.sporthouse.models.service.ShoppingCartServiceModel;
import com.eltosheva.sporthouse.services.ProductService;
import com.eltosheva.sporthouse.services.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/products")
@AllArgsConstructor
public class ProductsController {

    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;
    private final ModelMapper modelMapper;

    @GetMapping("/details/{id}")
    public String getProductDetails(@PathVariable String id, Model model) {
        if (!model.containsAttribute("shoppingCartBindingModel")) {
            model.addAttribute("shoppingCartBindingModel", new ShoppingCartBindingModel());
        }
        model.addAttribute("product", productService.findById(id));

        return "product_details";
    }

    @PostMapping("/add-to-cart")
    public String addProductToCart(@Valid @ModelAttribute ShoppingCartBindingModel shoppingCartBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("shoppingCartBindingModel", shoppingCartBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.shoppingCartBindingModel",
                    bindingResult);
            return "redirect:/products/details/" + shoppingCartBindingModel.getProductId();
        }
        shoppingCartService.addProductToCart(modelMapper.map(shoppingCartBindingModel, ShoppingCartServiceModel.class));
        return "redirect:/store";
    }

    @GetMapping("/shopping-cart")
    public String getShoppingCartProducts(Model model) {
        List<ShoppingCartServiceModel> cartContent = shoppingCartService.getAllUserProductsFromCart();
        model.addAttribute("products", cartContent);
        model.addAttribute("totalProductPrice", cartContent.stream()
                .map(p -> p.getTotalPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
        return "shopping-cart";
    }

    @GetMapping("/cart-remove/{id}")
    public String removeCartProduct(@PathVariable String id) {
        shoppingCartService.removeProductById(id);
        return "redirect:/products/shopping-cart";
    }
}
