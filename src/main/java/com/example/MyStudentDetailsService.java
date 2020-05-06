package com.example;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyStudentDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = repo.findByNetId(username);
        
        if (student == null)
            throw new UsernameNotFoundException("user not found");
        
        return new StudentPrincipal(student);
    }

}