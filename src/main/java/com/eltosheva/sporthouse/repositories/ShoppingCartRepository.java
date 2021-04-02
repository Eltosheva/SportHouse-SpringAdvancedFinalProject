package com.eltosheva.sporthouse.repositories;

import com.eltosheva.sporthouse.models.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
   long countShoppingCartByUser_Email(String email);
//   @Query("select sh.price as price, p.name as name " +
//           "from ShoppingCart sh left join Product p ON sh.productId=p.id")
   List<ShoppingCart> findAllByUser_Email(String email);
   @Transactional
   void deleteAllByUser_Email(String email);
}
