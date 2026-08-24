package com.example.onsenreviewapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onsenreviewapp.entity.Favorite;
import com.example.onsenreviewapp.entity.Onsen;
import com.example.onsenreviewapp.entity.User;
import com.example.onsenreviewapp.repository.FavoriteRepository;
import com.example.onsenreviewapp.repository.OnsenRepository;
import com.example.onsenreviewapp.repository.UserRepository;

@Service
public class FavoriteService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OnsenRepository onsenRepository;
	
	@Autowired
	private FavoriteRepository favoriteRepository;
	
	// ログインユーザーが指定した温泉をお気に入りに登録する
	// すでに登録済みの場合は何もせず処理を終了する
	public void addFavorite(String email, Long onsenId) {
		
		Optional<User> optionalUser = userRepository.findByEmail(email);
		
		if (optionalUser.isEmpty()) {
			return;
		}
		
		User user = optionalUser.get();
		
		Optional<Onsen> optionalOnsen = onsenRepository.findById(onsenId);
		
		if (optionalOnsen.isEmpty()) {
			return;
		}
		
		Onsen onsen = optionalOnsen.get();
		
		if (favoriteRepository.existsByUser_IdAndOnsen_Id(user.getId(), onsenId)) {
			return;
		}
		
		Favorite favorite = new Favorite();
		
		favorite.setUser(user);
		
		favorite.setOnsen(onsen);
		
		favoriteRepository.save(favorite);
	}
	
	// ログインユーザーの指定した温泉のお気に入り登録を解除する
	// お気に入りが存在しない場合は何もせず処理を終了する
	public void removeFavorite(String email, Long onsenId) {
		
		Optional<User> optionalUser = userRepository.findByEmail(email);
		
		if (optionalUser.isEmpty()) {
			return;
		}
		
		User user = optionalUser.get();
		
		Optional<Favorite> optionalFavorite = favoriteRepository.findByUser_IdAndOnsen_Id(user.getId(), onsenId);
		
		if (optionalFavorite.isEmpty()) {
			return;
		}
		
		Favorite favorite = optionalFavorite.get();
		
		favoriteRepository.delete(favorite);
		
	}
	
	// ログインユーザーが指定した温泉をお気に入り登録済みか確認する
	// 温泉詳細画面で登録ボタンと解除ボタンを切り替えるために使用する
	public boolean isFavorite(String email, Long onsenId) {
		
		Optional<User> optionalUser = userRepository.findByEmail(email);
		
		if (optionalUser.isEmpty()) {
			return false;
		}
		
		User user = optionalUser.get();
		
		boolean bool = favoriteRepository.existsByUser_IdAndOnsen_Id(user.getId(), onsenId);
		
		return bool;
		
	}
	
	// メールアドレスからユーザーを特定し、そのユーザーのお気に入り一覧を取得する
	// ユーザーが存在しない場合は空のリストを返す
	public List<Favorite> getFavoritesByEmail(String email) {
		
		Optional<User> optionalUser = userRepository.findByEmail(email);
		
		if (optionalUser.isEmpty()) {
			return List.of(); //ユーザーが存在しないため空のリストを返す
		}
		
		User user = optionalUser.get();
		
		List<Favorite> favorites = favoriteRepository.findByUser_Id(user.getId());
		
		return favorites;
	}
}
