package com.example.onsenreviewapp.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	//管理者トップ画面の表示
	@GetMapping("/admin")
	public String showAdminHome() {
		return "admin/index";
	}
}
