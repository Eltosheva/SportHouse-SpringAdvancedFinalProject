package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Product;
import com.eltosheva.sporthouse.models.entities.Sport;
import com.eltosheva.sporthouse.models.entities.Subscription;
import com.eltosheva.sporthouse.models.entities.SubscriptionProduct;
import com.eltosheva.sporthouse.models.service.ProductServiceModel;
import com.eltosheva.sporthouse.models.service.ProductStoreServiceModel;
import com.eltosheva.sporthouse.repositories.ProductRepository;
import com.eltosheva.sporthouse.repositories.SubscriptionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private ProductServiceImpl productServiceImpl;

    private ModelMapper modelMapper;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        productServiceImpl = new ProductServiceImpl(modelMapper, productRepository, subscriptionRepository);
    }

    @Test
    void addProduct() {
        ProductServiceModel product = new ProductServiceModel();
        product.setName("Ball");
        product.setPrice(BigDecimal.valueOf(10));
        product.setAvailableQuantity(10);
        product.setIsActive(true);

        productServiceImpl.addProduct(product);
        verify(productRepository, times(1)).saveAndFlush(modelMapper.map(product, Product.class));
    }

    @Test
    void getAllProducts() {
        Product p1 = new Product();
        p1.setId("id1");
        p1.setName("name1");

        Product p2 = new Product();
        p2.setId("id2");
        p2.setName("name2");

        when(productRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProductServiceModel> productList = productServiceImpl.getAllProducts();
        assertEquals(2, productList.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void addNewSubscriptionTypeProduct_valid() {
        Subscription subs = new Subscription();
        subs.setPrice(BigDecimal.valueOf(22.33));
        subs.setName("training");
        subs.setId("123");
        subs.setIsActive(true);

        SubscriptionProduct product = new SubscriptionProduct();
        product.setPrice(subs.getPrice());
        product.setName(subs.getName());
        product.setExternalId(subs.getId());
        product.setIsActive(subs.getIsActive());
        product.setAvailableQuantity(1);

        productServiceImpl.addNewSubscriptionTypeProduct(subs);
        verify(productRepository, times(1)).saveAndFlush(product);
    }

    @Test
    void findById_valid() {
        Product product = new Product();
        product.setId("123");
        product.setName("ball");
        when(productRepository.findById("123")).thenReturn(Optional.of(product));
        ProductServiceModel productServiceModel = productServiceImpl.findById("123");
        Assertions.assertEquals(productServiceModel.getId(), product.getId());
    }

    @Test
    void findById_illegalArgs() {
        when(productRepository.findById("invalidId")).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> productServiceImpl.findById("invalidId"));
    }

    @Test
    void changeStatus_valid() {
        Product product = new Product();
        product.setIsActive(false);
        when(productRepository.findById("id")).thenReturn(Optional.of(product));
        productServiceImpl.changeStatus("id");
        Assertions.assertEquals(product.getIsActive(), true);
    }

    @Test
    void changeStatus_illegalArgs() {
        when(productRepository.findById("invalidId")).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> productServiceImpl.changeStatus("invalidId"));
    }

    @Test
    void getTopThree() {
        SubscriptionProduct sp1 = new SubscriptionProduct();
        sp1.setPrice(BigDecimal.valueOf(11.22));
        SubscriptionProduct sp2 = new SubscriptionProduct();
        sp2.setPrice(BigDecimal.valueOf(40));
        SubscriptionProduct sp3 = new SubscriptionProduct();
        sp3.setPrice(BigDecimal.valueOf(14));
        SubscriptionProduct sp4 = new SubscriptionProduct();
        sp4.setPrice(BigDecimal.valueOf(50));

        when(productRepository.findAll()).thenReturn(List.of(sp1, sp2, sp3, sp4));

        List<ProductServiceModel> productList = productServiceImpl.getTopThree();

        assertEquals(3, productList.size());
        assertEquals(BigDecimal.valueOf(50), productList.get(0).getPrice());
        assertEquals(BigDecimal.valueOf(40), productList.get(1).getPrice());
        assertEquals(BigDecimal.valueOf(14), productList.get(2).getPrice());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getAllStoreProducts() {

        Sport sport = new Sport();
        sport.setId("123");

        Product p1 = new Product();
        p1.setSport(sport);

        SubscriptionProduct s1 = new SubscriptionProduct();

        when(productRepository.findAll()).thenReturn(List.of(p1, s1));

        List<ProductStoreServiceModel> storeList = productServiceImpl.getAllStoreProducts();

        assertEquals(2, storeList.size());
        verify(productRepository, times(1)).findAll();
    }
}