package com.example.onsenreviewapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onsenreviewapp.entity.Onsen;
import com.example.onsenreviewapp.repository.OnsenRepository;

@Service
public class OnsenService {
	
	@Autowired
	private OnsenRepository onsenRepository;
	
	public List<Onsen> getAllOnsens() {
		
		List<Onsen> onsens =  onsenRepository.findAll();
		
		return onsens;
	}
}
