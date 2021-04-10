package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Product;
import com.eltosheva.sporthouse.models.entities.ShoppingCart;
import com.eltosheva.sporthouse.models.entities.SubscriptionProduct;
import com.eltosheva.sporthouse.models.service.ShoppingCartServiceModel;
import com.eltosheva.sporthouse.repositories.ProductRepository;
import com.eltosheva.sporthouse.repositories.ShoppingCartRepository;
import com.eltosheva.sporthouse.repositories.UserRepository;
import com.eltosheva.sporthouse.services.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final EntityManagerFactory emf;

    @Override
    public void addProductToCart(ShoppingCartServiceModel shoppingCartServiceModel) {
        Product product = productRepository.findById(shoppingCartServiceModel.getProductId())
                .orElseThrow(IllegalArgumentException::new);
        if(!(product instanceof SubscriptionProduct)) {
            if (shoppingCartServiceModel.getQuantity() > product.getAvailableQuantity()) {
                throw new IllegalArgumentException();
            }
            product.setAvailableQuantity(product.getAvailableQuantity() - shoppingCartServiceModel.getQuantity());
            productRepository.saveAndFlush(product);
        }
            ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setProductId(shoppingCartServiceModel.getProductId());
        shoppingCart.setQuantity(shoppingCartServiceModel.getQuantity());
        shoppingCart.setPrice(product.getPrice());
        shoppingCart.setAddedAt(LocalDateTime.now());
        shoppingCart.setTotalPrice(product.getPrice().multiply(new BigDecimal(shoppingCartServiceModel.getQuantity())));
        shoppingCart.setUser(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(IllegalArgumentException::new));
        shoppingCartRepository.saveAndFlush(shoppingCart);
    }

    @Override
    public List<ShoppingCartServiceModel> getAllUserProductsFromCart() {
        EntityManager em = emf.createEntityManager();

        List<Object[]> results = em.createQuery("select s.price as price, s.quantity as quantity, " +
                " s.totalPrice as totalPrice, p.name as name, p.imageUrl as imageUrl, s.id as id"+
                " from ShoppingCart s inner join Product p on s.productId = p.id" +
                " inner join User u on s.user.id = u.id where u.email = ?1 " , Object[].class)
                .setParameter(1, SecurityContextHolder.getContext().getAuthentication().getName())
                .getResultList();

        List<ShoppingCartServiceModel> prodCartList = new ArrayList<>();

        for (Object[] row : results) {
            ShoppingCartServiceModel prod = new ShoppingCartServiceModel();
            prod.setPrice((BigDecimal) row[0]);
            prod.setQuantity((int) row[1]);
            prod.setTotalPrice((BigDecimal) row[2]);
            prod.setName((String) row[3]);
            prod.setImageUrl((String) row[4]);
            prod.setId((String) row[5]);

            prodCartList.add(prod);
        }

        em.close();
        return prodCartList;
    }

    @Override
    public void removeProductById(String id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        returnQuantity(shoppingCart.getProductId(), shoppingCart.getQuantity());
        shoppingCartRepository.deleteById(id);
    }

    @Async
    protected void returnQuantity(final String productId, final Integer quantity) {
        Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
        if(!(product instanceof SubscriptionProduct)) {
            product.setAvailableQuantity(quantity + product.getAvailableQuantity());
            productRepository.saveAndFlush(product);
        }
    }

}
