package com.example.onsenreviewapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.onsenreviewapp.entity.Review;
import com.example.onsenreviewapp.service.ReviewService;

@Controller
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/reviews/{reviewId}/edit")
	public String editReview(@PathVariable Long reviewId) {
		
		Optional<Review> review = reviewService.getReviewById(reviewId);
		
		if (review.isEmpty()) {
			
			return "redirect:/";
			
		}
		
		Review reviews = review.get();
	}
}
