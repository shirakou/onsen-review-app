package com.example.onsenreviewapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onsenreviewapp.entity.Review;
import com.example.onsenreviewapp.repository.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	public Review saveReview(Review review) {
		
		// save()はReviewをDBに保存し、保存後のReviewを返す
		Review saveReview =  reviewRepository.save(review);
		
		return saveReview;
	}
	
	//レビューの重複チェック
	public boolean hasReviewed(Long userId, Long onsenId) {
		
		return reviewRepository.existsByUser_IdAndOnsen_Id(userId, onsenId);
	}
	
	//指定した温泉のレビュー一覧を取得
	public List<Review> getReviewsByOnsenId(Long onsenId) {
		
		List<Review> reviews = reviewRepository.findByOnsen_Id(onsenId);
		
		return reviews;
	}
	
	public Optional<Review> getReviewById(Long reviewId) {
		
		Optional<Review> review = reviewRepository.findById(reviewId);
		
		return review;
	}
	
	public void deleteReview(Review review) {
		reviewRepository.delete(review);
	}
	
	//指定したUserのレビュー一覧を取得
	public List<Review> getReviewsByEmail(String email) {
		
		List<Review> reviews = reviewRepository.findByUser_Email(email);
		
		return reviews;
	}
	
	//全件取得
	public List<Review> getAllReviews() {
		
		List<Review> reviews = reviewRepository.findAll();
		
		return reviews;
	}
	
	//対象のレビューの削除
	public boolean deleteReviewById(Long reviewId) {
		
		Optional<Review> optionalReview = reviewRepository.findById(reviewId);
		
		if(optionalReview.isEmpty()) {
			return false;
		}
		
		Review review = optionalReview.get();
		
		reviewRepository.delete(review);
		
		return true;
	}
}
