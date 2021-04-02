package com.eltosheva.sporthouse.models.entities;

import com.eltosheva.sporthouse.models.enums.RoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role extends BaseEntity{
    @Column(name = "enum_name")
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null) return false;
        if (getClass() == o.getClass()) {
            Role role = (Role) o;
            return Objects.equals(name, role.name);
        } else if(o.getClass() == RoleEnum.class) {
            return String.valueOf(o).equals(name.name());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }
}
