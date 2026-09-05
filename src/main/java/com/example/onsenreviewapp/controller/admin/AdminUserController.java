package com.example.onsenreviewapp.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.onsenreviewapp.entity.User;
import com.example.onsenreviewapp.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
	
	
	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public String showAllUsers(Model model) {
		
		List<User> users = userService.getAllUsers();
		
		model.addAttribute("users", users);
		
		return "admin/users/index";
	}
	
	@PostMapping("/{userId}/deactivate")
	public String deactivateUser(
			@PathVariable Long userId) {
		
		boolean deactivated = userService.deactivateUser(userId);
		
		if (deactivated) {
			return "redirect:/admin/users";
		}
		
		return "redirect:/admin/users?statusError";
	}
	
	@PostMapping("/{userId}/activate")
	public String activateUser(
			@PathVariable Long userId) {
		
		
		boolean activated = userService.activateUser(userId);
		
		if(activated) {
			return "redirect:/admin/users";
		}
		
		return "redirect:/admin/users?statusError";
	}
}
