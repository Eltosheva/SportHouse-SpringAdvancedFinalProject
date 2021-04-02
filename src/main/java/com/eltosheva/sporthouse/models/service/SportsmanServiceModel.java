package com.eltosheva.sporthouse.models.service;

import com.eltosheva.sporthouse.models.entities.Subscription;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class SportsmanServiceModel extends UserServiceModel{
    private Integer age;
    private Double targetWeight;
    private Integer availableTrainings;
    private Set<Subscription> subscriptions = new HashSet<>();

}
