package com.example.onsenreviewapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.onsenreviewapp.entity.User;
import com.example.onsenreviewapp.form.SignupForm;
import com.example.onsenreviewapp.service.UserService;

import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class SignupController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/signup")
	public String showSignup(Model model) {
		SignupForm signupForm = new SignupForm();
		model.addAttribute("signupForm", signupForm);
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid SignupForm signupForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) { //バリデーションエラーがある場合
			return "signup";
		}
		
		User user = new User(); // SignupFormの入力値をUserに詰め替える
		user.setUsername(signupForm.getUsername());
		user.setEmail(signupForm.getEmail());
		user.setPassword(signupForm.getPassword());
		user.setBirthday(signupForm.getBirthday());
		user.setGender(signupForm.getGender());
		
		boolean result = userService.register(user);
		
		if(!result) { //resultがfalseの場合(メールアドレスが登録されている場合)
			bindingResult.rejectValue(
					"email",
					"duplicate",
					"このメールアドレスは既に登録されています"
					);
			return "signup";
		}
		return "redirect:/login"; // 登録成功後はログイン画面へリダイレクト
	}
}
