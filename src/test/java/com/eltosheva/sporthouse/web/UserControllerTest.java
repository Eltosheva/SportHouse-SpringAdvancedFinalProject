package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.models.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        User u1 = new User();
        u1.setId("123");
        u1.setEmail("1@1");
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "1@1";
            }
        });
    }

    @Test
    void userRegisterPage() throws Exception {
        this.mockMvc.perform(get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("sportsmanRegisterBindingModel"))
                .andExpect(model().attributeExists("isWrongConfirmPassword"))
                .andExpect(model().attributeExists("isFirstTime"))
                .andExpect(model().attributeExists("errMessage"))
                .andExpect(view().name("user/register"));
    }

    @Test
    void registerUser() {
    }

    @Test
    void createOrder() {
    }

    @Test
    void profile() throws Exception {

        this.mockMvc.perform(get("/user/orders"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("orders"))
                .andExpect(view().name("/user/orders"));
    }

    @Test
    void userProfileCorrection() {
    }

    @Test
    void settings() {
    }

    @Test
    void userOrdersPage() throws Exception {


        this.mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("profile"));
    }

    @Test
    void testUserSubscriptionsPage() throws Exception {
        this.mockMvc.perform(get("/user/mySubs"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("subs"))
                .andExpect(model().attributeExists("trainingCount"))
                .andExpect(view().name("/user/subscriptions"));
    }

    @Test
    void changePlaceStatus() throws Exception {
        MockHttpServletRequestBuilder createMessage = post("/user/schedule/add")
                .param("scheduleId", "123");
        this.mockMvc.perform(createMessage)
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/schedules?profilePicUrl=https%3A%2F%2Fih1.redbubble.net%2Fimage.161080070.3717%2Fflat%2C750x1000%2C075%2Cf.jpg&firstName=Admin&shoppingCartUserCount=0"));
    }
}