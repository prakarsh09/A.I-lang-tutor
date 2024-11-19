package com.creator.console.Creator.Console.JwtConfig;
import com.creator.console.Creator.Console.User;
import com.creator.console.Creator.Console.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch user by email
        User user = userRepo.findByEmail(email).orElseThrow(() -> 
            new UsernameNotFoundException("User not found with email: " + email)
        );

        // Return a Spring Security User with roles, authorities, and encrypted password
        return new UserPrincipal(user);
    }
}
