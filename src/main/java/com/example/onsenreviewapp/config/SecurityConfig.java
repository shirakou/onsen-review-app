package com.example.onsenreviewapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean //メソッドの戻り値をSpringで管理
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/login", "/signup").permitAll() //誰でもアクセス可能
				.anyRequest().authenticated() //それ以外はログイン必須
				);
		
		http.formLogin(form -> form
				.loginPage("/login") //ログイン画面のURL
				.permitAll() //ログイン画面は誰でもアクセス可能
				);
		
		return http.build(); //設定した内容からSecurityFilterChainを作成
	}
}
