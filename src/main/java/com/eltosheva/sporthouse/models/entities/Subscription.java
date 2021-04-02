package com.eltosheva.sporthouse.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
public class Subscription extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "training_count")
    private Integer trainingCount;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date startDate;

    @Column(name = "expire_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date expireDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_subscription",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = { @JoinColumn(name = "subscription_id", referencedColumnName = "id")})
    private Set<User> users = new HashSet<>();
}
