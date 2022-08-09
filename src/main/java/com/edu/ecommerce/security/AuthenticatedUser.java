package com.edu.ecommerce.security;

import com.edu.ecommerce.model.Role;
import com.edu.ecommerce.model.User;
import com.edu.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthenticatedUser {

    private final UserRepository userRepository;

    public Long getCurrentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var principal = auth.getPrincipal();
        Long id = null;
        if (principal instanceof UserDetails) {
            id = ((UserPrincipal) principal).getId();
        }
        return id;
    }

    public User getCurrentUser() {
        Long currentUserId = getCurrentUserId();
        return userRepository.getById(currentUserId);
    }

    public List<String> getRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public boolean hasRole(Role userRole) {
        var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return authorities.stream().
                anyMatch(a -> a.getAuthority().equals(userRole.name()));
    }
}