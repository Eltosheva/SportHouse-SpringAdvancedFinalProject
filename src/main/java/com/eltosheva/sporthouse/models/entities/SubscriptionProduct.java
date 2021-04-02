package com.eltosheva.sporthouse.models.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@DiscriminatorValue("S")
public class SubscriptionProduct extends Product {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "external_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Subscription subscription;
}
