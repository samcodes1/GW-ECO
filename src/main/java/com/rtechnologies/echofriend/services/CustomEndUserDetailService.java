package com.rtechnologies.echofriend.services;

import com.rtechnologies.echofriend.entities.user.UserEntity;
import com.rtechnologies.echofriend.models.security.CustomUserDetails;
import com.rtechnologies.echofriend.repositories.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomEndUserDetailService {

    @Autowired
    private UserRepo userRepository;

    public CustomUserDetails loadUserByUsername(String username)  {
        // Try to load a mentor
        Optional<UserEntity> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return CustomUserDetails.builder()
                    .username(user.get().getEmail())
                    .password(user.get().getPassword())
                    .roles(Set.of("ROLE_USER"))
                    .admin(null)
                    .userEntity(user.get())
                    .authorities(Collections.singletonList(() -> "ROLE_USER")) // Assuming "ROLE_TEACHER" as the authority for teachers
                    .build();
        }
        throw new NotFoundException("User not found with email: " + username);
    }
}


