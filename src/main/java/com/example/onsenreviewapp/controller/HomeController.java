package com.example.onsenreviewapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.onsenreviewapp.entity.Onsen;
import com.example.onsenreviewapp.entity.Review;
import com.example.onsenreviewapp.entity.User;
import com.example.onsenreviewapp.form.ReviewForm;
import com.example.onsenreviewapp.service.FavoriteService;
import com.example.onsenreviewapp.service.OnsenService;
import com.example.onsenreviewapp.service.ReviewService;
import com.example.onsenreviewapp.service.UserService;

import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	
	@Autowired
	private OnsenService onsenService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private FavoriteService favoriteService;
	
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
			Model model,
			Authentication authentication
			) {		
		
		Optional<Onsen> onsen = onsenService.getOnsenById(onsenId);
		
		//指定されたIDが存在しない場合
		if (onsen.isEmpty()) {
			return "redirect:/";
		}
		
		Onsen detailOnsen = onsen.get();
		
		boolean isFavorite = false;
		
		if (authentication != null) {
		    String email = authentication.getName();
		    isFavorite = favoriteService.isFavorite(email, onsenId);
		}

		model.addAttribute("isFavorite", isFavorite);
		
		//指定した温泉のレビュー一覧を取得
		List<Review> reviews = reviewService.getReviewsByOnsenId(onsenId);
		
		model.addAttribute("reviews", reviews);
		model.addAttribute("onsen", detailOnsen);
		model.addAttribute("reviewForm", new ReviewForm());
		
		return "onsen/detail";
	}
	
	@PostMapping("/onsens/{onsenId}/reviews")
	public String writeReview(
			@PathVariable Long onsenId,
			@Valid @ModelAttribute("reviewForm") ReviewForm reviewForm,
			BindingResult bindingResult,
			Authentication authenticator,
			Model model) { // 現在ログインしているユーザー情報を取得する
		
		// URLの温泉IDから投稿対象の温泉を取得
		Optional<Onsen> onsen = onsenService.getOnsenById(onsenId);
			
		if (onsen.isEmpty()) {
			return "redirect:/";
		}
		
		Onsen targetOnsen = onsen.get();
		
		if (bindingResult.hasErrors()) {
			
			List<Review> reviews =
		            reviewService.getReviewsByOnsenId(onsenId);
			
			model.addAttribute("onsen", targetOnsen);
			model.addAttribute("reviews", reviews);
			
			return "onsen/detail";
		}
		
		// ログインユーザーのメールアドレスを取得
		String email = authenticator.getName();
		
		// メールアドレスからログインユーザーを取得
		Optional<User> user = userService.getUserByEmail(email);
		
		if (user.isEmpty()) {
			
			return "redirect:/";
		}
		
		User loginUser = user.get();
				
		if (reviewService.hasReviewed(loginUser.getId(), targetOnsen.getId()) ) {
			
		    List<Review> reviews =
		            reviewService.getReviewsByOnsenId(onsenId);
		    
			model.addAttribute("onsen", targetOnsen);
			
			model.addAttribute("reviews", reviews);
			
			model.addAttribute("reviewError", "この温泉にはすでにレビューを投稿しています。");
			
			return "onsen/detail";
		}
		
		Review review = new Review();
			
		review.setUser(loginUser);
		review.setOnsen(targetOnsen);
		review.setTitle(reviewForm.getTitle());
		review.setRating(reviewForm.getRating());
		review.setComment(reviewForm.getComment());
		
		//レビューをDBに保存
		reviewService.saveReview(review);
		
		//投稿した温泉の詳細画面に再アクセス
		return "redirect:/onsens/" + onsenId;
	}
}
