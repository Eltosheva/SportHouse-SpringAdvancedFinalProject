package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.models.service.ProductStoreServiceModel;
import com.eltosheva.sporthouse.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class ProductsRestController {

    private final ProductService productService;

    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public ResponseEntity<List<ProductStoreServiceModel>> findAll() {
        return ResponseEntity.ok().body(productService.getAllStoreProducts());
    }
}
