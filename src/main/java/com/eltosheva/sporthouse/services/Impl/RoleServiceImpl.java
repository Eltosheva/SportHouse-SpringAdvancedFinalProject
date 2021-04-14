package com.eltosheva.sporthouse.services.Impl;

import com.eltosheva.sporthouse.models.entities.Role;
import com.eltosheva.sporthouse.models.entities.User;
import com.eltosheva.sporthouse.models.enums.RoleEnum;
import com.eltosheva.sporthouse.models.service.ChangeRoleServiceModel;
import com.eltosheva.sporthouse.models.service.RoleServiceModel;
import com.eltosheva.sporthouse.repositories.RoleRepository;
import com.eltosheva.sporthouse.repositories.UserRepository;
import com.eltosheva.sporthouse.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RoleServiceModel> getAllRoles() {
        return roleRepository
                .findAll()
                .stream()
                .map(role -> modelMapper.map(role, RoleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void changeUserRole(ChangeRoleServiceModel changeRole) {
        User user = userRepository.findById(changeRole.getUserId()).orElseThrow(IllegalArgumentException::new);
        Role role = roleRepository.findById(changeRole.getRole()).orElseThrow(IllegalArgumentException::new);
        if (role.getName().equals(RoleEnum.ADMIN)) {
            List<Role> roles = roleRepository
                    .findAll()
                    .stream()
                    .filter(r -> !r.getName().equals(RoleEnum.GUEST))
                    .collect(Collectors.toList());
            user.updateRoleSet(roles);
        } else {
            user.updateRoleSet(List.of(role));
        }
        userRepository.saveAndFlush(user);
    }
}
