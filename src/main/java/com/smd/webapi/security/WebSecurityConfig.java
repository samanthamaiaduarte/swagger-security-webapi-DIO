package com.smd.webapi.security;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private SecurityDatabaseService securityService;
    
    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
    	.authorizeHttpRequests(auth -> auth
        		.requestMatchers("/login").permitAll()
        		.requestMatchers(toH2Console()).permitAll()
        		
        		.requestMatchers(HttpMethod.GET, "/users/**").hasAnyRole("USER", "ADMIN")
        		
        		.requestMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN")
        		.requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN")
        		.requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
        		
        		.requestMatchers("/").hasRole("ADMIN")
        		.anyRequest().authenticated()
        	)
    	.csrf(AbstractHttpConfigurer::disable)
    	.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
    	.formLogin(Customizer.withDefaults()).logout(logout -> logout.logoutUrl("/logout"))
    	.httpBasic(Customizer.withDefaults());
    
        return http.build();
    }
    
}