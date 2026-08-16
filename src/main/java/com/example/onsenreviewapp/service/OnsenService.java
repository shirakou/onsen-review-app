package com.example.onsenreviewapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onsenreviewapp.entity.Onsen;
import com.example.onsenreviewapp.repository.OnsenRepository;

@Service
public class OnsenService {
	
	@Autowired
	private OnsenRepository onsenRepository;
	
	
	//全件取得
	public List<Onsen> getAllOnsens() {
		
		List<Onsen> onsens =  onsenRepository.findAll();
		
		return onsens;
	}
	
	//温泉名で検索
	public List<Onsen> searchByName(String name) {
		
		List<Onsen> onsens = onsenRepository.findByNameContaining(name);
		
		return onsens;
	}
	
	//都道府県で検索
	public List<Onsen> searchByPrefecture(String prefecture) {
		
		List<Onsen> onsens = onsenRepository.findByPrefecture(prefecture);
		
		return onsens;
	}
	
	public Optional<Onsen> getOnsenById(Long id) {
		
		Optional<Onsen> onsen = onsenRepository.findById(id);
		
		return onsen;
	}
}
