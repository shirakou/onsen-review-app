package com.example.onsenreviewapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.onsenreviewapp.entity.Review;
import com.example.onsenreviewapp.entity.User;
import com.example.onsenreviewapp.form.ReviewForm;
import com.example.onsenreviewapp.service.ReviewService;

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
}
