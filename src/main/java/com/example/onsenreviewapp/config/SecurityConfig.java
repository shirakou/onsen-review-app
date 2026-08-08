package com.example.onsenreviewapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
	
	@Bean //メソッドの戻り値をSpringで管理
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
