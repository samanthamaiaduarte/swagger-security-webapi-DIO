package com.smd.webapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smd.webapi.model.Login;
import com.smd.webapi.repository.LoginRepository;

@Service
public class LoginService {
	@Autowired
	private LoginRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public void createLogin(Login login) {
		login.setPassword(encoder.encode(login.getPassword()));
		repository.save(login);
	}
	
	public Login findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public boolean existsByUsername(String username) {
		return repository.existsByUsername(username);
	}
	
}
