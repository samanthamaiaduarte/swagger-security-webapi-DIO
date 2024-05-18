package com.smd.webapi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public String welcome() {
		return "Welcome to my Spring Boot Web API";
	}
}
