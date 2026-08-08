package com.example.onsenreviewapp.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import com.example.onsenreviewapp.form.SignupForm;

import org.springframework.ui.Model;

@Controller
public class SignupController {
	
	@GetMapping("/signup")
	public String showSignup(Model model) {
		SignupForm signupForm = new SignupForm();
		model.addAttribute("signupForm", signupForm);
		return "signup";
	}
}
