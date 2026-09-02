package com.example.onsenreviewapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onsenreviewapp.entity.Favorite;

public interface FavoriteRepository extends JpaRepository <Favorite, Long>{
	
	// 指定したユーザーが指定した温泉をお気に入り登録済みか確認する
	boolean existsByUser_IdAndOnsen_Id(
			Long userId,
			Long onsenId);
	
	// 指定したユーザーと温泉に対応するお気に入り情報を取得する
	Optional<Favorite> findByUser_IdAndOnsen_Id(
			Long userId,
			Long onsenId);
	
	//指定したユーザーのお気に入り一覧を取得する
	List<Favorite> findByUser_Id(
			Long userId);
	
	boolean existsByOnsen_Id(Long onsenId);
}
