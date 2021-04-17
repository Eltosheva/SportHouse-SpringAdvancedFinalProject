package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Product;
import com.eltosheva.sporthouse.models.entities.SubscriptionProduct;
import com.eltosheva.sporthouse.models.entities.User;
import com.eltosheva.sporthouse.models.entities.UserOrderHistory;
import com.eltosheva.sporthouse.models.service.UserOrderHistoryServiceModel;
import com.eltosheva.sporthouse.models.service.UserOrdersServiceModel;
import com.eltosheva.sporthouse.repositories.ProductRepository;
import com.eltosheva.sporthouse.repositories.ShoppingCartRepository;
import com.eltosheva.sporthouse.repositories.UserOrderHistoryRepository;
import com.eltosheva.sporthouse.repositories.UserRepository;
import com.eltosheva.sporthouse.services.UserOrderHistoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserOrderHistoryServiceImpl implements UserOrderHistoryService {

    private final AppMailService mailService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserOrderHistoryRepository userOrderHistoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createOrder() {
        AtomicInteger id = new AtomicInteger((int) System.currentTimeMillis() / 1000);
        if (id.intValue() < 0) {
            id.set(id.intValue() * -1);
            id.incrementAndGet();
        } else {
            id.incrementAndGet();
        }
        LocalDateTime dateAndTime = LocalDateTime.now();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<UserOrderHistory> historyList = shoppingCartRepository.findAllByUser_Email(email)
                .stream()
                .map(p -> modelMapper.map(p, UserOrderHistory.class))
                .collect(Collectors.toList());

        Integer trainingCounter = 0;
        for (UserOrderHistory element : historyList) {
            Product product = productRepository
                    .findById(element.getProductId())
                    .orElseThrow(IllegalArgumentException::new);
            element.setName(product.getName());
            element.setId("");
            element.setOrderId(id.intValue());
            element.setOrderDate(dateAndTime);
            if (product.getImageUrl() != null) {
                element.setImageUrl(product.getImageUrl());
            }
            if (product instanceof SubscriptionProduct) {
                trainingCounter += ((SubscriptionProduct) product).getSubscription().getTrainingCount();
            }
        }
        if (trainingCounter > 0) {
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                user.setAvailableTraining(user.getAvailableTraining() == null ? trainingCounter : user.getAvailableTraining() + trainingCounter);
                userRepository.saveAndFlush(user);
            }
        }

        userOrderHistoryRepository.saveAll(historyList);

        // Remove All products from clients cart
        shoppingCartRepository.deleteAllByUser_Email(email);

        // Prepare the evaluation context
        final Context ctx = new Context(Locale.ROOT);
        ctx.setVariable("productList", historyList);
        ctx.setVariable("totalProductPrice", historyList.stream()
                .map(UserOrderHistory::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        ctx.setVariable("userData", userRepository.findByEmail(email).get());
        ctx.setVariable("sendDate", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
        ctx.setVariable("sendDateTime", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()));

        try {
            mailService.sendSimpleMail(email, "Title", ctx);
        } catch (MessagingException e) {
        }
    }

    @Override
    public List<UserOrdersServiceModel> findAllUserOrders() {
        List<UserOrdersServiceModel> userOrdersServiceModels = new ArrayList<>();
        Map<Integer, List<UserOrderHistoryServiceModel>> orderMap = new HashMap<>();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        userOrderHistoryRepository.findAll().stream()
                .forEach(o -> {
                    if (!o.getUser().getEmail().equals(email))
                        return;
                    UserOrderHistoryServiceModel uhsm =
                            modelMapper.map(o, UserOrderHistoryServiceModel.class);
                    if (!orderMap.containsKey(uhsm.getOrderId())) {
                        orderMap.put(uhsm.getOrderId(), new ArrayList<>());
                    }
                    orderMap.get(uhsm.getOrderId()).add(uhsm);
                });

        orderMap.entrySet().forEach(entry -> {
            UserOrdersServiceModel userOrdersServiceModel = new UserOrdersServiceModel();
            userOrdersServiceModel.setOrderList(entry.getValue());
            userOrdersServiceModel.setOrderId(entry.getKey());
            userOrdersServiceModel.setTotalOrderPrice(entry.getValue().stream()
                    .map(UserOrderHistoryServiceModel::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
            userOrdersServiceModel.setOrderDate(entry.getValue().get(0).getOrderDate());
            userOrdersServiceModels.add(userOrdersServiceModel);
        });
        return userOrdersServiceModels;
    }

    @Override
    public List<UserOrdersServiceModel> findAllUserSubscriptions() {
        List<UserOrdersServiceModel> userOrdersServiceModels = new ArrayList<>();
        Map<Integer, List<UserOrderHistoryServiceModel>> orderMap = new HashMap<>();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        userOrderHistoryRepository.findAll().stream()
                .forEach(o -> {
                    if (!o.getUser().getEmail().equals(email))
                        return;
                    Product product = productRepository.findById(o.getProductId()).orElse(null);
                    if (!(product instanceof SubscriptionProduct)) return;
                    UserOrderHistoryServiceModel uhsm =
                            modelMapper.map(o, UserOrderHistoryServiceModel.class);
                    if (!orderMap.containsKey(uhsm.getOrderId())) {
                        orderMap.put(uhsm.getOrderId(), new ArrayList<>());
                    }
                    orderMap.get(uhsm.getOrderId()).add(uhsm);
                });
        orderMap.forEach((key, value) -> {
            UserOrdersServiceModel userOrdersServiceModel = new UserOrdersServiceModel();
            userOrdersServiceModel.setOrderList(value);
            userOrdersServiceModel.setOrderId(key);
            userOrdersServiceModel.setTotalOrderPrice(value.stream()
                    .map(UserOrderHistoryServiceModel::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
            userOrdersServiceModel.setOrderDate(value.get(0).getOrderDate());
            userOrdersServiceModels.add(userOrdersServiceModel);
        });
        return userOrdersServiceModels;
    }
}