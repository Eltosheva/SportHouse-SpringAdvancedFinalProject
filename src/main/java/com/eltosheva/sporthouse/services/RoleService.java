package com.eltosheva.sporthouse.services;

import com.eltosheva.sporthouse.models.service.ChangeRoleServiceModel;
import com.eltosheva.sporthouse.models.service.RoleServiceModel;

import java.util.List;

public interface RoleService {
    List<RoleServiceModel> getAllRoles();

    void changeUserRole(ChangeRoleServiceModel changeRole);
}
