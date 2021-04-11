package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Product;
import com.eltosheva.sporthouse.models.entities.ShoppingCart;
import com.eltosheva.sporthouse.models.service.ProductServiceModel;
import com.eltosheva.sporthouse.models.service.UserOrderHistoryServiceModel;
import com.eltosheva.sporthouse.repositories.ProductRepository;
import com.eltosheva.sporthouse.repositories.ShoppingCartRepository;
import com.eltosheva.sporthouse.repositories.UserOrderHistoryRepository;
import com.eltosheva.sporthouse.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserOrderHistoryServiceImplTest {

    private static final String USER_EMAIL = "1@1";
    private UserOrderHistoryServiceImpl userOrderHistoryService;

    @Mock
    private UserOrderHistoryRepository userOrderHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductServiceModel productServiceModel;

    @Mock
    private AppMailService mailService;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        userOrderHistoryService = new UserOrderHistoryServiceImpl(mailService,shoppingCartRepository,
                userOrderHistoryRepository, userRepository, productRepository, modelMapper);
    }

    @Test
    @WithMockUser(value = USER_EMAIL)
    void createOrder_validTest() {
        Product product1 = new Product();
        product1.setId("12345asd");
        product1.setName("testProduct");
        product1.setImageUrl("");
        when(productRepository.findById("13245asd")).thenReturn(java.util.Optional.of(product1));

        ShoppingCart cart1 = new ShoppingCart();
        cart1.setProductId("12345asd");

        when(shoppingCartRepository.findAllByUser_Email(USER_EMAIL)).thenReturn(List.of(cart1));
    }

    @Test
    void createOrder_invalidTest() {

    }

    @Test
    void findAllUserOrders_validTest() {

    }

    @Test
    void findAllUserOrders_invalidTest() {

    }
}