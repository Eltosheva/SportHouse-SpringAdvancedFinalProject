package com.eltosheva.sporthouse.models.entities;
import com.eltosheva.sporthouse.models.enums.RoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNum;

    @Column(name = "age")
    private Integer age;

    @Column(name = "profile_pic_url")
    private String profilePictureUrl;

    @Column(name = "target_weight")
    private Double targetWeight;

    @Column(name = "description")
    private String description;

    @Column(name = "social_media_url")
    private String socialMediaUrl;

    @Column(name = "available_trainings")
    private Integer availableTraining;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Subscription> subscriptions= new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Schedule> schedules = new HashSet<>();

    @OneToOne
    private Sport sport;

    public User updateRoleSet(List<Role> roles) {
        this.roles = roles == null ? new HashSet<>() : new HashSet<>(roles);
        return this;
    }

    public boolean hasRole(RoleEnum role) {
        return this.roles.stream().anyMatch(r -> r.equals(role));
    }

    public boolean isAdmin() {
        return hasRole(RoleEnum.ADMIN);
    }

    public boolean isUser() {
        return hasRole(RoleEnum.USER);
    }

    public boolean isCoach() {
        return hasRole(RoleEnum.COACH);
    }

}
