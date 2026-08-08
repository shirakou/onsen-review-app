package com.example.onsenreviewapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onsenreviewapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	// 会員登録時に、メールアドレスが既に登録されているか確認する
	boolean existsByEmail(String email);

	// ログイン時に、メールアドレスからユーザー情報を取得する
	Optional<User> findByEmail(String email);
}
