package com.eltosheva.sporthouse.web;

import com.eltosheva.sporthouse.models.service.ChangeRoleServiceModel;
import com.eltosheva.sporthouse.services.RoleService;
import com.eltosheva.sporthouse.utils.ResponseMsg;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class RoleRestController {

    private final RoleService roleService;

    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(path = "/changeUserRole", method = RequestMethod.POST)
    public ResponseEntity<?> changeRole(ChangeRoleServiceModel changeRole) {
        roleService.changeUserRole(changeRole);
        return ResponseEntity.ok().body(new ResponseMsg(true));
    }
}
