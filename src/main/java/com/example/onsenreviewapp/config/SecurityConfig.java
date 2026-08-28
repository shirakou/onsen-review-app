package com.example.onsenreviewapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.onsenreviewapp.service.CustomUserDetailsService;


@Configuration
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean //メソッドの戻り値をSpringで管理
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/login", "/signup").permitAll()
				.requestMatchers(HttpMethod.GET, "/onsens/*").permitAll()//誰でもアクセス可能
				.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
				.anyRequest().authenticated() //それ以外はログイン必須
				);
		
		http.formLogin(form -> form
				.loginPage("/login") //ログイン画面のURL
				.permitAll() //ログイン画面は誰でもアクセス可能
				.defaultSuccessUrl("/", true) //ログイン成功後の遷移先
				);
		
		http.logout(logout -> logout
				.logoutSuccessUrl("/login") //ログアウト成功後の遷移先
				);
		
		http.authenticationProvider(authenticationProvider()); //ユーザー取得方法とパスワード照合方法を設定
		
		return http.build(); //設定した内容からSecurityFilterChainを作成
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() { //認証の設定
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
		
		provider.setPasswordEncoder(passwordEncoder());
		
		return provider;
	}
}
