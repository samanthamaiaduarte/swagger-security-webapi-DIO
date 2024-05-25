package com.smd.webapi.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smd.webapi.model.Login;
import com.smd.webapi.service.LoginService;

@Service
public class SecurityDatabaseService implements UserDetailsService {
	
    @Autowired
    private LoginService service;
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        
    	Login logins = service.findByUsername(username);
        if (logins == null) {
            throw new UsernameNotFoundException(username);
        }
        
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        logins.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        });
        
        UserDetails user = new User(logins.getUsername(), logins.getPassword(), authorities);
        
        return user;
    }
}