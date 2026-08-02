package com.example.onsenreviewapp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id //主キーに設定
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 登録時にDB側でIDを自動採番
	@Column(name = "user_id")
	private Long id;
	
	@Column(name = "username", nullable = false, length = 50)
	private String username;
	
	@Column(name = "email", nullable = false, unique = true, length = 255)
	private String email;
	
	@Column(name = "password", nullable = false, length = 255)
	private String password;
	
	@Column(name = "birthday", nullable = false)
	private LocalDate birthday;
	
	@Column(name = "gender")
	private Short gender;
	
	@Column(name = "role", nullable = false, length = 20)
	private String role;
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at",nullable = false)
	private LocalDateTime updatedAt;
	
	@Column(name = "is_active", nullable = false)
	private Boolean isActive;
}
