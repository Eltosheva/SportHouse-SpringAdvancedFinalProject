package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.models.service.ChangeRoleServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductsRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void getAllProducts_returnProductsList() throws Exception {
        ChangeRoleServiceModel changeRole = new ChangeRoleServiceModel();
        changeRole.setRole("ADMIN");

        this.mockMvc.perform(get("/api/changeUserRole")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .requestAttr("changeRole", changeRole)
                .with(user("1@1").password("123456")))
                .andExpect(status().is(405));
                //.andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")));
    }

}
