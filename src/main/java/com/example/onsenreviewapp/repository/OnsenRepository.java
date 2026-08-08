package com.example.onsenreviewapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.onsenreviewapp.entity.Onsen;

public interface OnsenRepository extends JpaRepository<Onsen, Long> {
	
	// 温泉名に指定した文字列を含む温泉を検索する
	List<Onsen> findByNameContaining(String name);
	
	// 都道府県が一致する温泉を検索する
	List<Onsen> findByPrefecture(String prefecture);
}
