package com.example.onsenreviewapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.onsenreviewapp.entity.Favorite;
import com.example.onsenreviewapp.entity.User;
import com.example.onsenreviewapp.form.ProfileForm;
import com.example.onsenreviewapp.service.FavoriteService;
import com.example.onsenreviewapp.service.UserService;

@Controller
public class MyPageController {

	@Autowired
	private FavoriteService favoriteService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/mypage")
	public String showMyPage(
			Authentication authentication,
			Model model) {
		
		String email = authentication.getName();
		
		List<Favorite> favorites = favoriteService.getFavoritesByEmail(email);
		
		model.addAttribute("favorites", favorites);
		
		return "mypage/index";
	}
	
	@GetMapping("/mypage/profile")
	public String showProfileEdit(
			Authentication authentication,
			Model model
			) {
		
		String email = authentication.getName();
		
		Optional<User> optionalUser = userService.getUserByEmail(email);
		
		if (optionalUser.isEmpty()) {
			return "redirect:/";
		}
		
		User user = optionalUser.get();
		
		ProfileForm profileForm = new ProfileForm();
		
		profileForm.setUsername(user.getUsername());
		
		model.addAttribute("profileForm", profileForm);
		
		return "mypage/profile";
	}
}
