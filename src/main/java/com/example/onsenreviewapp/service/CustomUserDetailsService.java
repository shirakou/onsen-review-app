package com.example.onsenreviewapp.service;

import java.util.*;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.onsenreviewapp.entity.User;
import com.example.onsenreviewapp.repository.UserRepository;

//UserDetailsServiceはSpring Securityがログイン時にユーザー情報を取得するためのインターフェース
//implementsすることで loadUserByUsername() を実装する必要がある
@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		Optional<User> user = userRepository.findByEmail(username);
		
		if(user.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}
		
		User loginUser = user.get();
		
		return new org.springframework.security.core.userdetails.User(
				loginUser.getEmail(),
				loginUser.getPassword(),
				List.of(new SimpleGrantedAuthority(loginUser.getRole()))
				);
	}
}
