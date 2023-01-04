package com.gogi1000.datecourse.service.review.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Review;
import com.gogi1000.datecourse.entity.ReviewId;
import com.gogi1000.datecourse.repository.ReviewRepository;
import com.gogi1000.datecourse.service.review.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Override
	public Page<CamelHashMap> getReviewList(Review review, Pageable pageable) {
		System.out.println("getReviewList Impl :" + review);
		if(review.getSearchKeywordCmt() != null && !review.getSearchKeywordCmt().equals("")) {
			if(review.getSearchConditionCmt().equals("ALL")) {
				return reviewRepository.getReviewListAll(review, pageable);
			}
			else if(review.getSearchConditionCmt().equals("NAME")) {
				return reviewRepository.getReviewListName(review, pageable);
			}
			else if(review.getSearchConditionCmt().equals("ID")) {
				return reviewRepository.getReviewListId(review, pageable);
			}
			else if(review.getSearchConditionCmt().equals("COMMENT")) {
				return reviewRepository.getReviewListComment(review, pageable);
			}
			else {
				return reviewRepository.getReviewList(pageable);
			}
		}
		else {
			return reviewRepository.getReviewList(pageable);
		}
		
		
	}
	
	@Override
	public void insertReview(Review review) {
		int reviewNo = reviewRepository.getNextReviewNo(review.getDatecourseNo());
		
		review.setReviewNo(reviewNo);
		
		reviewRepository.save(review);
	}
	
	@Override
	public void updateReview(Review review) {
		reviewRepository.save(review);
	}
	
	@Override
	public void deleteReview(int reviewNo, int datacourseNo) {
		ReviewId reviewId = new ReviewId();
		reviewId.setDatecourseNo(datacourseNo);
		reviewId.setReviewNo(reviewNo);
		reviewRepository.deleteById(reviewId);
		// reviewRepository.
	}
	
	@Override
	public void deleteReviewList(List<Review> reviewList) {
		reviewRepository.deleteAll(reviewList);
	}
	
	@Override
	public Review reviewModel(Review review) {
		System.out.println("Impl : " + review);
		
		return reviewRepository.selectModal(review);
	}
}
