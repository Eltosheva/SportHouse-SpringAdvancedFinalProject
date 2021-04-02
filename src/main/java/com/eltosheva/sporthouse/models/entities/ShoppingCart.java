package com.eltosheva.sporthouse.models.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shopping_cart")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ShoppingCart extends BaseEntity {
    @Column
    private BigDecimal price;

    @Column
    private Integer quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "added_at")
    @DateTimeFormat(pattern = " ddd, dd MMMM yyyy HH:mm")
    private LocalDateTime addedAt;

    @ManyToOne
    private User user;

    private String productId;
}