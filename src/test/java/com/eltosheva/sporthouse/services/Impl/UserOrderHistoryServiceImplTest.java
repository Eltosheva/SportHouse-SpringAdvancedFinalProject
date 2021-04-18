package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.*;
import com.eltosheva.sporthouse.models.service.ProductServiceModel;
import com.eltosheva.sporthouse.models.service.UserOrderHistoryServiceModel;
import com.eltosheva.sporthouse.models.service.UserOrdersServiceModel;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

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
    @WithMockUser(value = USER_EMAIL)
    void createOrder_validTest() {
        ShoppingCart s1 = new ShoppingCart();
        s1.setId("12345asd");
        s1.setQuantity(10);
        s1.setProductId("13245asd");
        s1.setTotalPrice(BigDecimal.valueOf(10));

        /*ShoppingCart s2 = new ShoppingCart();
        s2.setId("12345asd");
        s2.setQuantity(10);
        s2.setProductId("13245");*/
        when(shoppingCartRepository.findAllByUser_Email("1@1")).thenReturn(List.of(s1)); /*, s2*/

        Product product = new Product();
        product.setId("22");
        product.setName("testName");


        Subscription subs = new Subscription();
        subs.setTrainingCount(1);

        SubscriptionProduct sp1 = new SubscriptionProduct();
        sp1.setAvailableQuantity(22);
        sp1.setId("321");
        sp1.setName("sub1");
        sp1.setSubscription(subs);

        when(productRepository.findById("13245asd")).thenReturn(Optional.of(sp1));

        User u = new User();
        u.setId("333");
        u.setEmail("pvgeorgiev89@yahoo.com");
        u.setAvailableTraining(0);
        when(userRepository.findByEmail("1@1")).thenReturn(Optional.of(u));

        userOrderHistoryService.createOrder();

        verify(userOrderHistoryRepository, times(0)).saveAll(List.of(modelMapper.map(s1, UserOrderHistory.class)));
        verify(shoppingCartRepository, times(1)).deleteAllByUser_Email("1@1");
    }

    @Test
    void findAllUserOrders() {

        User u = new User();
        u.setEmail("1@1");

        UserOrderHistory uh1 = new UserOrderHistory();
        uh1.setUser(u);
        uh1.setOrderId(123);
        uh1.setTotalPrice(BigDecimal.valueOf(100));
        uh1.setOrderDate(LocalDateTime.now());

        when(userOrderHistoryRepository.findAll()).thenReturn(List.of(uh1));

        List<UserOrdersServiceModel> userOrdersServiceModels = userOrderHistoryService.findAllUserOrders();

        assertEquals(1, userOrdersServiceModels.size());
    }

    @Test
    void findAllUserSubscriptions() {
        User u = new User();
        u.setEmail("1@1");

        UserOrderHistory uh1 = new UserOrderHistory();
        uh1.setUser(u);
        uh1.setOrderId(123);
        uh1.setTotalPrice(BigDecimal.valueOf(100));
        uh1.setOrderDate(LocalDateTime.now());
        uh1.setProductId("1111");


        SubscriptionProduct sp1 = new SubscriptionProduct();

        when(userOrderHistoryRepository.findAll()).thenReturn(List.of(uh1));

        when(productRepository.findById("1111")).thenReturn(Optional.of(sp1));

        List<UserOrdersServiceModel> userOrdersServiceModels = userOrderHistoryService.findAllUserSubscriptions();

        assertEquals(1, userOrdersServiceModels.size());
    }

}