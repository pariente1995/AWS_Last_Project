package com.gogi1000.datecourse.service.review;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Review;
import com.gogi1000.datecourse.entity.ReviewId;

public interface ReviewService {
	
	// 리뷰리스트 출력 및 검색 기능 구현_장찬영
	Page<CamelHashMap> getReviewList(Review review, Pageable pageable);
	
	// 리뷰 등록_장찬영
	Review insertReview(Review review);
	
	// 리뷰 수정(완전하지 않음)_장찬영
	Review updateReview(Review review);
	
	// 리뷰 삭제_장찬영
	void deleteReview(ReviewId reviewId);
	
	// 리뷰 리스트 삭제_장찬영
	void deleteReviewList(List<Review> reviewList);
	
	// 상세 리뷰 출력(모달)_장찬영
	Review reviewModel(Review review);
	
	// 상세 게시글 댓글 출력_장찬영
	List<Review> getCommentList(int datecourseNo);
}
