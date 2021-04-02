package com.eltosheva.sporthouse.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_order_history")
public class UserOrderHistory extends BaseEntity{

    @Column(name = "order_id")
    private Integer orderId;

    @Column
    private BigDecimal price;

    @Column
    private String name;

    @Column
    private Integer quantity;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "added_at")
    @DateTimeFormat(pattern = " ddd, dd MMMM yyyy HH:mm")
    private LocalDateTime addedAt;

    @Column(name = "order_date")
    @DateTimeFormat(pattern = " ddd, dd MMMM yyyy HH:mm")
    private LocalDateTime orderDate;

    @ManyToOne
    private User user;

    @Column(name = "product_id")
    private String productId;
}
