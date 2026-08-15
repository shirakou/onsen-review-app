package com.example.onsenreviewapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onsenreviewapp.entity.Onsen;
import com.example.onsenreviewapp.service.OnsenService;

@Controller
public class HomeController {
	
	
	@Autowired
	private OnsenService onsenService;
	
	@GetMapping("/")
	public String showHome(@RequestParam(required = false) String name, Model model) {
		
		List<Onsen> onsens;
		
		if (name == null || name.isBlank()) {
			//全件取得
			onsens = onsenService.getAllOnsens();
		} else {
			//nameで検索
			onsens = onsenService.searchByName(name);
		}
		
		model.addAttribute("onsens", onsens);
		
		return "home";
	}
}
