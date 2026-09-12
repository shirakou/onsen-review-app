package com.example.onsenreviewapp.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.onsenreviewapp.entity.Review;

public class ReviewServiceTest {
	
	@Test
	void calculatesAverageRatingForMultipleReviews() {
		
		//準備・評価1のレビュー一覧を作成する
		Review review1 = new Review();
		review1.setRating((short)1);
		
		//準備・評価4のレビュー一覧を作成する
		Review review2 = new Review();
		review2.setRating((short)4);
		
		List<Review> reviews = new ArrayList<Review>();
		
		reviews.add(review1);
		reviews.add(review2);
		
		ReviewService reviewService = new ReviewService();
		
		double averageRating = reviewService.calculateAverageRating(reviews);
		
		Assertions.assertEquals(2.5, averageRating, 0.0001);
	}
	
	@Test
	void returnsZeroWhenReviewsAreEmpty() {
		
		List<Review> reviews = new ArrayList<Review>();
		
		ReviewService reviewService = new ReviewService();
		
		double averageRating = reviewService.calculateAverageRating(reviews);
		
		Assertions.assertEquals(0.0, averageRating, 0.0001);
	}
}
