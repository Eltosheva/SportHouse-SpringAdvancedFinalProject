package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Product;
import com.eltosheva.sporthouse.models.entities.Subscription;
import com.eltosheva.sporthouse.models.entities.SubscriptionProduct;
import com.eltosheva.sporthouse.models.service.ProductServiceModel;
import com.eltosheva.sporthouse.models.service.ProductStoreServiceModel;
import com.eltosheva.sporthouse.repositories.ProductRepository;
import com.eltosheva.sporthouse.repositories.SubscriptionRepository;
import com.eltosheva.sporthouse.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final SubscriptionRepository subscriptionRepository;

    public ProductServiceImpl(ModelMapper modelMapper, ProductRepository productRepository, SubscriptionRepository subscriptionRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public void addProduct(ProductServiceModel productServiceModel) {
        Product product = modelMapper.map(productServiceModel, Product.class);
        productRepository.saveAndFlush(product);
    }

    @Transactional
    @Override
    public List<ProductServiceModel> getAllProducts() {
        List<ProductServiceModel> productServiceModels = new ArrayList<>();
        productRepository
                .findAll()
                .forEach(product -> {
                    ProductServiceModel productServiceModel = new ProductServiceModel();
                    modelMapper.map(product, productServiceModel);
                    productServiceModels.add(productServiceModel);
                });
        return productServiceModels;
    }

    @Transactional
    @Override
    public List<ProductStoreServiceModel> getAllStoreProducts() {
        List<ProductStoreServiceModel> productServiceModels = new ArrayList<>();
        productRepository
                .findAll()
                .forEach(product -> {
                    ProductStoreServiceModel productServiceModel = new ProductStoreServiceModel();
                    modelMapper.map(product, productServiceModel);
                    if(product instanceof SubscriptionProduct) {
                        productServiceModel.setType('S');
                    } else {
                        productServiceModel.setType('P');
                        productServiceModel.setSportId(product.getSport().getId());
                    }
                    productServiceModels.add(productServiceModel);
                });
        return productServiceModels;
    }

    @Override
    public void addNewSubscriptionTypeProduct(Subscription subscription) {
        SubscriptionProduct product = new SubscriptionProduct();
        product.setPrice(subscription.getPrice());
        product.setName(subscription.getName());
        product.setExternalId(subscription.getId());
        product.setIsActive(subscription.getIsActive());
        product.setAvailableQuantity(1);

        productRepository.saveAndFlush(product);
    }

    @Override
    public ProductServiceModel findById(String id) {
        return productRepository.findById(id)
                .map(entity -> modelMapper.map(entity, ProductServiceModel.class))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<ProductServiceModel> getTopThree() {
        return productRepository.findAll()
                .stream()
                .filter(product -> product instanceof SubscriptionProduct)
                .sorted(Comparator.comparing(Product::getPrice).reversed())
                .limit(3)
                .map(s ->  {
                    ProductServiceModel productServiceModel = modelMapper.map(s, ProductServiceModel.class);
                    Subscription subscription = subscriptionRepository.findById(productServiceModel.getExternalId()).orElse(null);
                    if(subscription != null) productServiceModel.setTrainingCount(subscription.getTrainingCount());
                    return productServiceModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void changeStatus(String id) {
        Product product = productRepository.findById(id).orElseThrow(IllegalAccessError::new);
        product.setIsActive(!product.getIsActive());
        productRepository.saveAndFlush(product);
    }
}
