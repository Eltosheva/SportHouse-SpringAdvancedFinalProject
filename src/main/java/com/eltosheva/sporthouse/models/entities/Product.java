package com.eltosheva.sporthouse.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.CHAR, columnDefinition = "char(1)")
@DiscriminatorValue("P")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {
    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column(name = "image_Url")
    private String imageUrl;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "available_quantity")
    private Integer availableQuantity;

    @Expose(serialize = false,deserialize = false)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Sport sport;

    @Column(name = "external_id")
    private String externalId;
}
