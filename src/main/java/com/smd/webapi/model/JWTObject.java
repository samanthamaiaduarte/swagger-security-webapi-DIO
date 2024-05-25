package com.smd.webapi.model;

import java.time.Instant;
import java.util.List;

public class JWTObject {
    private String subject; //nome do usuario
    private Instant issuedAt; //data de criação do token
    private Instant expiration; // data de expiração do token
    private List<String> roles; //perfis de acesso
    
    public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Instant getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Instant issuedAt) {
		this.issuedAt = issuedAt;
	}

	public Instant getExpiration() {
		return expiration;
	}

	public void setExpiration(Instant expiration) {
		this.expiration = expiration;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles){
        this.roles = roles;
    }
		
}