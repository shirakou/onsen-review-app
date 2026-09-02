package com.example.onsenreviewapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onsenreviewapp.entity.Review;

public interface ReviewRepository extends JpaRepository <Review, Long >{
	
	// Reviewのonsen.idが指定した温泉IDと一致するレビューをすべて取得する
	List<Review> findByOnsen_Id(Long onsenId);
	
	// 指定したユーザーが指定した温泉にレビュー投稿済みか確認する
	boolean existsByUser_IdAndOnsen_Id(Long userId, Long onsenId);
	
	//指定したメールアドレスのユーザーが投稿したレビューを全て取得する
	List<Review> findByUser_Email(String email); 
	
	boolean existsByOnsen_Id(Long onsenId);
}
