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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CoachControllerTest {

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
    void manageCoachSchedule() throws Exception {
        this.mockMvc.perform(get("/coach/schedules"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("scheduleBindingModel"))
                .andExpect(model().attributeExists("isFirstTime"))
                .andExpect(model().attributeExists("schedules"))
                .andExpect(model().attributeExists("places"))
                .andExpect(view().name("coach/schedule"));
    }

    @Test
    void testManageCoachSchedule() {
    }

    @Test
    void coachRegisterPage() throws Exception {
        this.mockMvc.perform(get("/coach/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("coachRegisterBindingModel"))
                .andExpect(model().attributeExists("isWrongConfirmPassword"))
                .andExpect(model().attributeExists("isFirstTime"))
                .andExpect(model().attributeExists("errMessage"))
                .andExpect(model().attributeExists("sports"))
                .andExpect(view().name("coach/register"));
    }

    @Test
    void registerCoach() {
    }

    @Test
    void coachProfilePage() throws Exception {
        this.mockMvc.perform(get("/coach/profile"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("sports"))
                .andExpect(view().name("profile"));
    }

    @Test
    void coachProfileCorrection() {
    }
}