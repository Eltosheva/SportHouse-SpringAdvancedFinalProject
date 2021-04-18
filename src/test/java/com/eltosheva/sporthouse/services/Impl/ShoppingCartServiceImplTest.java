package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Product;
import com.eltosheva.sporthouse.models.entities.ShoppingCart;
import com.eltosheva.sporthouse.models.entities.User;
import com.eltosheva.sporthouse.models.service.ShoppingCartServiceModel;
import com.eltosheva.sporthouse.repositories.ProductRepository;
import com.eltosheva.sporthouse.repositories.ShoppingCartRepository;
import com.eltosheva.sporthouse.repositories.UserRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles(profiles = "non-async")
class ShoppingCartServiceImplTest {

    private static final String USER_EMAIL = "1@1";

    private ShoppingCartServiceImpl shoppingCartServiceImpl;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EntityManagerFactory emf;
    @Mock
    EntityManager em;

    @BeforeEach
    void setUp() {
        shoppingCartServiceImpl = new ShoppingCartServiceImpl(shoppingCartRepository,
                productRepository, userRepository, emf);
    }

    @Test
    @WithMockUser(username = USER_EMAIL)
    void addProductToCart_validProduct() {
        Authentication auth = Mockito.mock(Authentication.class);
        SecurityContext secCont = Mockito.mock(SecurityContext.class);
        Mockito.when(secCont.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(secCont);
        Mockito.when(auth.getName()).thenReturn(USER_EMAIL);

        ShoppingCartServiceModel shoppingCartServiceModel = new ShoppingCartServiceModel();
        shoppingCartServiceModel.setQuantity(10);
        shoppingCartServiceModel.setProductId("1111");

        Product product = new Product();
        product.setAvailableQuantity(12);
        product.setPrice(BigDecimal.valueOf(12.12));

        when(productRepository.findById("1111")).thenReturn(Optional.of(product));
        when(userRepository.findByEmail("1@1")).thenReturn(Optional.of(new User()));
        shoppingCartServiceImpl.addProductToCart(shoppingCartServiceModel);
        verify(productRepository, times(1)).saveAndFlush(product);
    }

    @Test
    void addProductToCart_notEnoughQuantity() {
        ShoppingCartServiceModel shoppingCartServiceModel = new ShoppingCartServiceModel();
        shoppingCartServiceModel.setQuantity(22);
        shoppingCartServiceModel.setProductId("1111");

        Product product = new Product();
        product.setAvailableQuantity(12);
        product.setPrice(BigDecimal.valueOf(12.12));

        when(productRepository.findById("1111")).thenReturn(Optional.of(product));
        Assertions.assertThrows(IllegalArgumentException.class, () -> shoppingCartServiceImpl.addProductToCart(shoppingCartServiceModel));
    }

   /* @Test
    @Ignore("Test is not ready yet")
    @WithMockUser(username = USER_EMAIL)
    void getAllUserProductsFromCart() {

        Authentication auth = Mockito.mock(Authentication.class);
        SecurityContext secCont = Mockito.mock(SecurityContext.class);
        Mockito.when(secCont.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(secCont);
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

        Mockito.when(emf.createEntityManager()).thenReturn(em);

        EntityManager entityManager = Mockito.mock(EntityManager.class);

        List<Object[]> expected = new ArrayList<>();
        expected.add(new Object[] { 12.2, 1, 12.2, "pro1", "", "id1"});
        expected.add(new Object[] { 3.4, 2, 6.8, "pro2", "", "id2" });

        when(entityManager.createQuery("select s.price as price, s.quantity as quantity, " +
                " s.totalPrice as totalPrice, p.name as name, p.imageUrl as imageUrl, s.id as id"+
                " from ShoppingCart s inner join Product p on s.productId = p.id" +
                " inner join User u on s.user.id = u.id where u.email = ?1 ", Object[].class)
                .setParameter(1, "1@1")
                .getResultList())
                .thenReturn(expected);

        List<ShoppingCartServiceModel> cartList = shoppingCartServiceImpl.getAllUserProductsFromCart();

        assertEquals(expected.size(), cartList.size());
        assertEquals(2, cartList.size());
    }*/

    @Test
    void removeProductById_illegalCartId() {
        when(shoppingCartRepository.findById("123")).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> shoppingCartServiceImpl.removeProductById("123"));
    }

    @Test
    void removeProductById_valid() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setProductId("2211");
        shoppingCart.setQuantity(12);

        when(shoppingCartRepository.findById("123")).thenReturn(Optional.of(shoppingCart));

        Product product = new Product();
        product.setId("123");
        product.setAvailableQuantity(0);

        when(productRepository.findById("2211")).thenReturn(Optional.of(product));

        shoppingCartServiceImpl.removeProductById("123");
        verify(shoppingCartRepository, times(1)).deleteById("123");
    }

    private void setAuthentication() {

    }
}