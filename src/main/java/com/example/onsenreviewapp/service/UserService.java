package com.example.onsenreviewapp.service;

import java.util.Optional;

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
	
	// 新規ユーザーを登録する
	// メールアドレスが登録済みの場合はfalseを返す
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

	// emailに対応するユーザー情報を取得する
	public Optional<User> getUserByEmail(String email) {
		
		Optional<User> user = userRepository.findByEmail(email);
		
		return user;
	}
	
	// emailに対応するユーザーのusernameを更新する
	// ユーザーが存在しない場合はfalseを返す
	public boolean updateUsername(String email, String username) {
		
		Optional<User> optionalUser = userRepository.findByEmail(email);
		
		if (optionalUser.isEmpty()) {
			return false;
		}
		
		User user = optionalUser.get();
		
		user.setUsername(username);
		
		userRepository.save(user);
		
		return true;
	}
	
	// emailに対応するユーザーのisActiveをfalseにして退会状態にする
	// ユーザーが存在しない場合はfalseを返す
	public boolean withdrawUser(String email) {
		
		Optional<User> optionalUser = userRepository.findByEmail(email);
		
		if (optionalUser.isEmpty()) {
			return false;
		}
		
		User user = optionalUser.get();
		
		user.setIsActive(false);
		
		userRepository.save(user);
		
		return true;
	}
}
