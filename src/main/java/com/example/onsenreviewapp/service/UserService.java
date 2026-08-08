package com.example.onsenreviewapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.onsenreviewapp.entity.User;
import com.example.onsenreviewapp.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//新規会員登録
public boolean register(User user) {
		
		// メールアドレスが既に登録されている場合は登録しない
		if (userRepository.existsByEmail(user.getEmail())) {
			return false;
		}
		
		user.setRole("ROLE_USER");
		user.setIsActive(true);
		
		String password = user.getPassword(); //passwordを取得
		String encoderPassword = passwordEncoder.encode(password); //ハッシュ化
		user.setPassword(encoderPassword);
		
		// 未登録の場合はユーザーを保存
		userRepository.save(user);
		return true;
	}
}
