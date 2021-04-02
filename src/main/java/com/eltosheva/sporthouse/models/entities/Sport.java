package com.eltosheva.sporthouse.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sports")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Sport extends BaseEntity{

    @Expose
    @Column(name = "name")
    private String name;

    @Expose
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Expose
    @Column(name = "image_Url")
    private String imageUrl;

   /* @JsonIgnore
    @Expose(serialize = false, deserialize = false)
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "sports")
    private Set<User> users = new HashSet<>();*/
}