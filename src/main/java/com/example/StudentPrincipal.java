package com.example;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class StudentPrincipal implements UserDetails {
    
    private String ROLE_PREFIX = "ROLE_";
    private Student user;

    public StudentPrincipal(Student user) {
        super();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getRole();
        if (role == null) role = "USER"; 
        return Collections.singleton(new SimpleGrantedAuthority(ROLE_PREFIX + role));
    }
    
    @Override
    public String getPassword() {
        // System.out.println("PASSWORD: "+user.getPassword());
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getNetID();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }

}