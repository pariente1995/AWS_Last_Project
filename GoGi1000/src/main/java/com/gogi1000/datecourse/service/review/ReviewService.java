package com.gogi1000.datecourse.service.review;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Review;

public interface ReviewService {
	Page<CamelHashMap> getReviewList(Review review, Pageable pageable);
	
	void insertReview(Review review);
	
	void updateReview(Review review);
	
	void deleteReview(int reviewNo, int datacourseNo);
	
	void deleteReviewList(List<Review> reviewList);
	
	Review reviewModel(Review review);
}
