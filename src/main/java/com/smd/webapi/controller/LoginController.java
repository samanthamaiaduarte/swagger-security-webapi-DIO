package com.smd.webapi.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smd.webapi.dto.Access;
import com.smd.webapi.dto.Session;
import com.smd.webapi.model.JWTObject;
import com.smd.webapi.model.Login;
import com.smd.webapi.security.JWTCreator;
import com.smd.webapi.security.SecurityConfig;
import com.smd.webapi.service.LoginService;

@RestController
public class LoginController {
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private LoginService service;

	@PostMapping("/login")
	public Session logar(@RequestBody Access access) {
		Login login = service.findByUsername(access.getUsername());
		
		if (login != null) {
			boolean passwordOk = encoder.matches(access.getPassword(), login.getPassword());
			if (!passwordOk) {
				throw new RuntimeException("Invalid password for login: " + access.getUsername());
			}			

			Session session = new Session();
			session.setLogin(login.getUsername());

			JWTObject jwtObject = new JWTObject();
			jwtObject.setSubject(login.getUsername());
			jwtObject.setIssuedAt(Instant.now());
			jwtObject.setExpiration(Instant.now().plusMillis(SecurityConfig.EXPIRATION));
			jwtObject.setRoles(login.getRoles());
			
			session.setToken(JWTCreator.create(SecurityConfig.PREFIX, SecurityConfig.KEY, jwtObject));
			
			return session;
		} else {
			throw new RuntimeException("Login error");
		}
	}
}
