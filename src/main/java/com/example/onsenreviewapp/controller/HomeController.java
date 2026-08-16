package com.example.onsenreviewapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.onsenreviewapp.entity.Onsen;
import com.example.onsenreviewapp.service.OnsenService;

@Controller
public class HomeController {
	
	
	@Autowired
	private OnsenService onsenService;
	
	@GetMapping("/")
	public String showHome(
							@RequestParam(required = false) String name,
							@RequestParam(required = false) String prefecture,
							Model model) {
		
		List<Onsen> onsens;
		
		if (name != null && !name.isBlank()) {
			//名前検索
			onsens = onsenService.searchByName(name);
		} else if(prefecture != null && !prefecture.isBlank()) {
			//都道府県検索
			onsens = onsenService.searchByPrefecture(prefecture);
			
		} else {
			//両方空
			onsens = onsenService.getAllOnsens();
		}
		
		model.addAttribute("onsens", onsens);
		
		return "home";
	}
	
	@GetMapping("/onsens/{onsenId}") //URLのonsenIdを使って温泉詳細を表示
	public String showDetail(
			@PathVariable Long onsenId, //URLのonsenIdを受け取る
			Model model
			) {		
		
		Optional<Onsen> onsen = onsenService.getOnsenById(onsenId);
		
		//指定されたIDが存在しない場合
		if (onsen.isEmpty()) {
			return "redirect:/";
		}
		
		Onsen detailOnsen = onsen.get();
		
		model.addAttribute("onsen", detailOnsen);
		
		return "onsen/detail";
	}
}
