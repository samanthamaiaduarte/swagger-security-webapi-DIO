package com.smd.webapi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_login")
public class Login {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idlogin")
	private Integer id;
	@Column(length = 50, nullable = false)
	private String name;
	@Column(length = 20, nullable = false)
	private String username;
	@Column(length = 100, nullable = false)
	private String password;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tb_login_roles", joinColumns = @JoinColumn(name = "idlogin"))
	@Column(name = "idrole")
	private List<String> roles = new ArrayList<>();
	
	public Login() { }
	
	public Login(String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRoles() {
		return roles;
	}
	
	public void addRole(String role) {
		this.roles.add(role);
	}

}
