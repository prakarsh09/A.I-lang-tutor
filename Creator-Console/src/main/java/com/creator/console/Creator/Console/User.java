package com.creator.console.Creator.Console;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Creators")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long id;
   private String name;
   private String email;
   private String password;
   private String credit;
    
}
