package com.example.onsenreviewapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.onsenreviewapp.entity.Review;
import com.example.onsenreviewapp.entity.User;
import com.example.onsenreviewapp.form.ReviewForm;
import com.example.onsenreviewapp.service.ReviewService;

import jakarta.validation.Valid;

@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/reviews/{reviewId}/edit")
	public String editReview(
			@PathVariable Long reviewId,
			Authentication authenticator,
			Model model
			) {
		
		Optional<Review> review = reviewService.getReviewById(reviewId);
		
		if (review.isEmpty()) {			
			return "redirect:/";			
		}
		
		Review targetReview = review.get();
		
		String loginEmail = authenticator.getName();
		
		User user = targetReview.getUser();
		 
		String reviewUserEmail = user.getEmail();
		
		if (!loginEmail.equals(reviewUserEmail)) {
			return "redirect:/onsens/" + targetReview.getOnsen().getId();
		}
		
		ReviewForm reviewForm = new ReviewForm();
		
		reviewForm.setTitle(targetReview.getTitle());
		reviewForm.setRating(targetReview.getRating());
		reviewForm.setComment(targetReview.getComment());
		
		// 編集画面で現在のレビュー内容を使用するため
		model.addAttribute("reviewForm", reviewForm);
		

		// 更新対象のレビューIDを編集画面で使用するため
		model.addAttribute("reviewId", reviewId);
		
		return "review/edit";
		
	}
	
	@PostMapping("/reviews/{reviewId}/edit")
	public String updateReview(
			@PathVariable Long reviewId,
			@Valid ReviewForm reviewForm,
			BindingResult bindingResult,
			Authentication authenticator,
			Model model
			) {
		
		Optional<Review> targetReview = reviewService.getReviewById(reviewId);
		
		if (targetReview.isEmpty()) {
			return "redirect:/";
		}
		
		//Optionalから更新対象のReviewを取り出す
		Review review = targetReview.get();
		
		//review投稿したユーザーのemailを取得
		String reviewEmail = review.getUser().getEmail();
		
		//現在ログインしているユーザーのemailを取得
		String email = authenticator.getName();
		
		//ログインしているユーザーとreview投稿したユーザーが正しいか
		if (!reviewEmail.equals(email)) {
			return "redirect:/onsens/" + review.getOnsen().getId();
		}
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("reviewId", reviewId);
			return "review/edit";
		}
		
		review.setTitle(reviewForm.getTitle());
		review.setRating(reviewForm.getRating());
		review.setComment(reviewForm.getComment());
		
		reviewService.saveReview(review);
		
		
		return "redirect:/onsens/" + review.getOnsen().getId();
		
	}
}
