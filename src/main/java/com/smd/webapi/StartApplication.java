package com.smd.webapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.smd.webapi.model.Login;
import com.smd.webapi.service.LoginService;

@Component
public class StartApplication implements CommandLineRunner {
    @Autowired
    private LoginService service;
    
    @Transactional
	@Override
	public void run(String... args) throws Exception {
    	Login login = service.findByUsername("teste");
    	if(login == null) {
    		login = new Login("TESTE", "teste", "Teste123");
    		login.addRole("USER");
    		service.createLogin(login);
    	}
    	
    	login = service.findByUsername("admin");
    	if(login == null) {
    		login = new Login("ADMIN", "admin", "Teste456");
    		login.addRole("ADMIN");
    		service.createLogin(login);
    	}
	}

}
