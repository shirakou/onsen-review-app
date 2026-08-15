package com.example.onsenreviewapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.onsenreviewapp.entity.Onsen;
import com.example.onsenreviewapp.service.OnsenService;

@Controller
public class HomeController {
	
	
	@Autowired
	private OnsenService onsenService;
	
	@GetMapping("/")
	public String showHome(Model model) {
		
		List<Onsen> onsens = onsenService.getAllOnsens();
		
		model.addAttribute("onsens", onsens);
		
		return "home";
	}
}
