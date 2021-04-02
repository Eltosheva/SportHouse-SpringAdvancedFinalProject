package com.eltosheva.sporthouse.security;

import com.eltosheva.sporthouse.models.entities.User;
import com.eltosheva.sporthouse.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User with email" + email + " is not fount."));

        return mapToUserDetails(user);
    }

    private UserDetails mapToUserDetails(User user) {
        List<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map(role ->
                        new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),authorities);
    }

}
