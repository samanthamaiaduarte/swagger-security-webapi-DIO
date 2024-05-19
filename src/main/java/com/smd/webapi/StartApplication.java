package com.smd.webapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.smd.webapi.model.Login;
import com.smd.webapi.repository.LoginRepository;

@Component
public class StartApplication implements CommandLineRunner {
    @Autowired
    private LoginRepository repository;
    
    @Transactional
	@Override
	public void run(String... args) throws Exception {
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	
    	Login user = repository.findByUsername("teste");
    	if(user == null) {
    		user = new Login("TESTE", "teste", encoder.encode("Teste123"));
    		user.addRole("USER");
    		repository.save(user);
    	}
    	
    	user = repository.findByUsername("admin");
    	if(user == null) {
    		user = new Login("ADMIN", "admin", encoder.encode("Teste456"));
    		user.addRole("ADMIN");
    		repository.save(user);
    	}
		
	}

}
