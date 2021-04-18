package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.services.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProductsControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ShoppingCartService shoppingCartService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void getProductDetails() {
    }

    @Test
    void addProductToCart() {
    }

//    @Test
//    void getShoppingCartProducts() throws Exception {
//
//        this.mockMvc.perform(get("/products/shopping-cart"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("products"))
//                .andExpect(model().attributeExists("totalProductPrice"))
//                .andExpect(view().name("shopping-cart"));
//    }

    @Test
    void removeCartProduct() {
    }
}