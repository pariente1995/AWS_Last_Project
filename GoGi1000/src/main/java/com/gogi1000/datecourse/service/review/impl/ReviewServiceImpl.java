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
	private ReviewRepository reviewRepository;
	
	// 리뷰리스트 출력 및 검색 기능 구현
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
	
	// 리뷰 등록_장찬영
	@Override
	public Review insertReview(Review review) {
		// 리뷰 넘버 부여
		int reviewNo = reviewRepository.getNextReviewNo(review.getDatecourseNo());		
				
		review.setReviewNo(reviewNo);
		
		reviewRepository.save(review);	
		reviewRepository.flush();
		
		return review;
	}
	
	// 리뷰 수정(완전하지 않음)
	@Override
	public Review updateReview(Review review) {
		reviewRepository.save(review);
		reviewRepository.flush();
		
		return review;
	}
	
	// 리뷰 삭제
	@Override
	public void deleteReview(ReviewId reviewId) {		
		reviewRepository.deleteById(reviewId);		
	}
	
	// 리뷰 리스트 삭제
	@Override
	public void deleteReviewList(List<Review> reviewList) {
		reviewRepository.deleteAll(reviewList);
	}
	
	// 상세 리뷰 출력(모달)
	@Override
	public Review reviewModel(Review review) {
		return reviewRepository.selectModal(review);
	}
	
	// 상세 게시글 리뷰리스트 출력_장찬영
	@Override
	public List<Review> getCommentList(int datecourseNo) {
		return reviewRepository.getCommentList(datecourseNo);
	}
}
