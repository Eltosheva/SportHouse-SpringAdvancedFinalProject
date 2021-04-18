package com.eltosheva.sporthouse.web;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void manageSportsPage() throws Exception {
        this.mockMvc.perform(get("/admin/sports"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("sportBindingModel"))
                .andExpect(model().attributeExists("isFirstTime"))
                .andExpect(model().attributeExists("sports"))
                .andExpect(view().name("admin/sports"));
    }

    @Test
    void addNewSport() {
    }

    @Test
    void manageHallsPage() throws Exception {
        this.mockMvc.perform(get("/admin/halls"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("placeBindingModel"))
                .andExpect(model().attributeExists("isFirstTime"))
                .andExpect(model().attributeExists("isPlaceSavedSuccessfully"))
                .andExpect(model().attributeExists("halls"))
                .andExpect(view().name("admin/halls"));
    }

    @Test
    void addNewPlace() {
    }

    @Test
    void manageProductsPage() throws Exception {
        this.mockMvc.perform(get("/admin/products"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("productBindingModel"))
            .andExpect(model().attributeExists("isFirstTime"))
            .andExpect(model().attributeExists("isProductSavedSuccessfully"))
            .andExpect(model().attributeExists("sports"))
            .andExpect(model().attributeExists("products"))
            .andExpect(view().name("admin/products"));
    }

    @Test
    void addProduct() {
    }

    @Test
    void manageSubscriptionsPage() throws Exception {
        this.mockMvc.perform(get("/admin/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("subscriptionBindingModel"))
                .andExpect(model().attributeExists("subscriptionBindingModel"))
                .andExpect(model().attributeExists("isFirstTime"))
                .andExpect(model().attributeExists("isSubscriptionSavedSuccessfully"))
                .andExpect(model().attributeExists("subscriptions"))
                .andExpect(view().name("admin/subscriptions"));
    }

    @Test
    void addNewPlan() {
    }

    @Test
    void manageTasksPage() throws Exception {
        this.mockMvc.perform(get("/admin/tasks"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("jobs"))
            .andExpect(view().name("admin/tasks"));
    }

//    @Ignore
//    @Test
//    void manageUsersPage() throws Exception {
//        this.mockMvc.perform(get("/admin/users"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("roles"))
//                .andExpect(view().name("admin/users"));
//    }
//
//    @Ignore
//    @Test
//    void adminProfilePage() throws Exception {
//        this.mockMvc.perform(get("/admin/profile"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("user"))
//                .andExpect(model().attributeExists("sports"));
//    }

    @Test
    void changeUserStatus() throws Exception {
        MockHttpServletRequestBuilder createMessage = post("/admin/users/status")
                .param("email", "1@1");

        this.mockMvc.perform(createMessage)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin/users"));
    }

    @Test
    void addNewPlan_post() throws Exception {
        MockHttpServletRequestBuilder createMessage = post("/admin/subscriptions")
                .param("name", "Test")
                .param("price", "12.25")
                .param("trainingCount", "2")
                .param("startDate", "2021-05-18")
                .param("expireDate", "2021-05-18")
                .param("isActive", "true");

        this.mockMvc.perform(createMessage)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/admin/subscriptions"));
    }
}