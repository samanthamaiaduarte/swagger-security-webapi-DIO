package com.smd.webapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Bean
    InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
    	UserBuilder users = User.builder();
		UserDetails user = users
				.username("teste")
				.password(encoder.encode("Teste123"))
				.roles("USER")
				.build();
		UserDetails admin = users
				.username("admin")
				.password(encoder.encode("Teste456"))
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(user, admin);
    }
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
    	.csrf(AbstractHttpConfigurer::disable)
    	.formLogin(Customizer.withDefaults()).logout(logout -> logout.logoutUrl("/logout"))
    	.httpBasic(Customizer.withDefaults());
    
    return http.build();
}
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}