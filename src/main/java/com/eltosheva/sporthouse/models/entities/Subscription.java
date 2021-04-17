package com.eltosheva.sporthouse.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

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
}
