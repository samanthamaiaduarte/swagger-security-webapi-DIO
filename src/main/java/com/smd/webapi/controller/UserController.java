package com.smd.webapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smd.webapi.model.User;
import com.smd.webapi.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository repository;
	
	@GetMapping
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public List<User> getUsers() {
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public User getUserById(@PathVariable("id") Integer id) {
		return repository.findById(id);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public void postUser(@RequestBody User user) {
		repository.save(user);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public void putUser(@RequestBody User user) {
		repository.save(user);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(@PathVariable("id") Integer id) {
		repository.deleteById(id);
	}
}
