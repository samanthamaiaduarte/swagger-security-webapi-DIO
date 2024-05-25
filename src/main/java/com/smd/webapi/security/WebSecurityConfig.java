package com.smd.webapi.security;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] SWAGGER_WHITELIST = {"/v3/api-docs","/swagger-resources","/swagger-resources/**","/configuration/ui","/configuration/security","/api-docs.html","/swagger-ui.html","/webjars/**"};	
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
    	.authorizeHttpRequests(auth -> auth
        		.requestMatchers(SWAGGER_WHITELIST).permitAll()
        		.requestMatchers(toH2Console()).permitAll()
        		
        		.requestMatchers(HttpMethod.POST, "/login").permitAll()
        		
        		.requestMatchers(HttpMethod.GET, "/").hasAnyRole("ADMIN","USER")
        		
        		.requestMatchers(HttpMethod.GET, "/users/**").hasAnyRole("USER", "ADMIN")
        		
        		.requestMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN")
        		.requestMatchers(HttpMethod.PUT, "/users/**").hasRole("ADMIN")
        		.requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
        		
        		.anyRequest().authenticated()
        	)
    	.addFilterAfter(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
    	.csrf(AbstractHttpConfigurer::disable)
    	.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
    	.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    
        return http.build();
    }
    
    @Bean
    PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}