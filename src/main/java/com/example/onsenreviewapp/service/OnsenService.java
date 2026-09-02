package com.example.onsenreviewapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onsenreviewapp.entity.Onsen;
import com.example.onsenreviewapp.form.OnsenForm;
import com.example.onsenreviewapp.repository.FavoriteRepository;
import com.example.onsenreviewapp.repository.OnsenRepository;
import com.example.onsenreviewapp.repository.ReviewRepository;

@Service
public class OnsenService {
	
	@Autowired
	private OnsenRepository onsenRepository;
	
	@Autowired
	private FavoriteRepository favoriteRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	
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
	
	// 管理者画面で温泉を登録する
	public Onsen createOnsen(OnsenForm onsenForm) {

	    Onsen onsen = new Onsen();

	    onsen.setName(onsenForm.getName());
	    onsen.setImageUrl(onsenForm.getImageUrl());
	    onsen.setAddress(onsenForm.getAddress());
	    onsen.setPhone(onsenForm.getPhone());
	    onsen.setBusinessHours(onsenForm.getBusinessHours());
	    onsen.setHoliday(onsenForm.getHoliday());
	    onsen.setAdultPrice(onsenForm.getAdultPrice());
	    onsen.setChildPrice(onsenForm.getChildPrice());
	    onsen.setNearestStation(onsenForm.getNearestStation());
	    onsen.setStationTime(onsenForm.getStationTime());
	    onsen.setPrefecture(onsenForm.getPrefecture());

	    onsen.setFamilyBath(onsenForm.isFamilyBath());
	    onsen.setSauna(onsenForm.isSauna());
	    onsen.setOpenAirBath(onsenForm.isOpenAirBath());
	    onsen.setRestaurant(onsenForm.isRestaurant());
	    onsen.setAlcohol(onsenForm.isAlcohol());
	    onsen.setTowelRental(onsenForm.isTowelRental());
	    onsen.setParking(onsenForm.isParking());

	    onsen.setDescription(onsenForm.getDescription());

	    Onsen savedOnsen = onsenRepository.save(onsen);

	    return savedOnsen;
	}
	
	//管理者画面で温泉を更新する
	public boolean updateOnsen(Long onsenId, OnsenForm onsenForm) {
		
		Optional<Onsen> optionalOnsen = onsenRepository.findById(onsenId);
		
		if (optionalOnsen.isEmpty()) {
			return false;
		}
		
		Onsen onsen = optionalOnsen.get();
		
		onsen.setName(onsenForm.getName());
		onsen.setImageUrl(onsenForm.getImageUrl());
		onsen.setAddress(onsenForm.getAddress());
		onsen.setPhone(onsenForm.getPhone());
		onsen.setBusinessHours(onsenForm.getBusinessHours());
		onsen.setHoliday(onsenForm.getHoliday());
		onsen.setAdultPrice(onsenForm.getAdultPrice());
		onsen.setChildPrice(onsenForm.getChildPrice());
		onsen.setNearestStation(onsenForm.getNearestStation());
		onsen.setStationTime(onsenForm.getStationTime());
		onsen.setPrefecture(onsenForm.getPrefecture());
		onsen.setFamilyBath(onsenForm.isFamilyBath());
		onsen.setSauna(onsenForm.isSauna());
		onsen.setOpenAirBath(onsenForm.isOpenAirBath());
		onsen.setRestaurant(onsenForm.isRestaurant());
		onsen.setAlcohol(onsenForm.isAlcohol());
		onsen.setTowelRental(onsenForm.isTowelRental());
		onsen.setParking(onsenForm.isParking());
		onsen.setDescription(onsenForm.getDescription());
		
		onsenRepository.save(onsen);
		
		return true;
	}
	
	public boolean deleteOnsen(Long onsenId) {
		
		Optional<Onsen> optionalOnsen = onsenRepository.findById(onsenId);
		
		if (optionalOnsen.isEmpty()) {
			return false;
		}
		
		Onsen onsen = optionalOnsen.get();
		
		boolean favoriteBoolean =  favoriteRepository.existsByOnsen_Id(onsenId);
		
		boolean reviewBoolean = reviewRepository.existsByOnsen_Id(onsenId);
		
		if (favoriteBoolean || reviewBoolean) {
			return false;
		}
		
		onsenRepository.delete(onsen);
		
		return true;
	}
}
