package com.example.onsenreviewapp.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.onsenreviewapp.entity.Onsen;
import com.example.onsenreviewapp.service.OnsenService;

@Controller
@RequestMapping("/admin/onsens")
public class AdminOnsenController {
	
	@Autowired
	private OnsenService onsenService;
	
	@GetMapping("")
	public String showAllOnsens(Model model) {
		
		List<Onsen> onsens = onsenService.getAllOnsens();
		
		model.addAttribute("onsens", onsens);
		
		return "admin/onsens/index";
	}
}
