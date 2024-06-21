package com.auth.entity;

import jakarta.persistence.*;

@Entity
//@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return role;
	}

	public void setName(String role) {
		this.role = role;
	}

    // Getters and Setters
}

