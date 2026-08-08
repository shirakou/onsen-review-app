package com.example.onsenreviewapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onsenreviewapp.entity.Favorite;

public interface FavoriteRepository extends JpaRepository <Favorite, Long>{
	
	// 指定したユーザーが指定した温泉をお気に入り登録済みか確認する
	boolean existsByUser_IdAndOnsen_Id(
			Long userId,
			Long onsenId);
}
