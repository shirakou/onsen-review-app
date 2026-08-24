package com.example.onsenreviewapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.onsenreviewapp.service.FavoriteService;

@Controller
public class FavoriteController {
	
	@Autowired
	private FavoriteService favoriteService;
	
	
	@PostMapping("/onsens/{onsenId}/favorite")
	public String addFavorite(
			@PathVariable Long onsenId,
			Authentication authentication) {
		
		String email = authentication.getName();
		
		favoriteService.addFavorite(email, onsenId);
		
		return "redirect:/onsens/" + onsenId;
		
	}
	
	@PostMapping("/onsens/{onsenId}/favorite/delete")
	public String removeFavorite(
			@PathVariable Long onsenId,
			Authentication authentication) {
		
		String email = authentication.getName();
		
		favoriteService.removeFavorite(email, onsenId);
		
		return "redirect:/onsens/" + onsenId;
		
	}
}
