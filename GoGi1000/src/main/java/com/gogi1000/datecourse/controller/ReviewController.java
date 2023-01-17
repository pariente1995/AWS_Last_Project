package com.gogi1000.datecourse.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.dto.ResponseDTO;
import com.gogi1000.datecourse.dto.ReviewDTO;
import com.gogi1000.datecourse.entity.CustomUserDetails;
import com.gogi1000.datecourse.entity.Review;
import com.gogi1000.datecourse.entity.ReviewId;
import com.gogi1000.datecourse.service.review.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
	@Autowired
	private ReviewService reviewService; 
	
	// 관리자페이지 - 댓글 리스트 화면으로 이동 및 리뷰 리스트 출력_장찬영
    @GetMapping("/adminpageComment")
    public ModelAndView mypageLikeListView(ReviewDTO reviewDTO,
    		@PageableDefault(page=0, size=15) Pageable pageable) {
        ModelAndView mv = new ModelAndView();
        
        Review review = Review.builder()
        						.searchConditionCmt(reviewDTO.getSearchConditionCmt())
        						.searchKeywordCmt(reviewDTO.getSearchKeywordCmt())
        						.build();
        						
        
        Page<CamelHashMap> reviewList = reviewService.getReviewList(review, pageable);
        
        
        mv.setViewName("review/adminpageComment.html");
        mv.addObject("reviewList", reviewList);
        
        if(reviewDTO.getSearchConditionCmt() != null && !reviewDTO.getSearchConditionCmt().equals("")) {
			mv.addObject("searchConditionCmt", reviewDTO.getSearchConditionCmt());
		}	
		if(reviewDTO.getSearchKeywordCmt() != null && !reviewDTO.getSearchKeywordCmt().equals("")) {
			mv.addObject("searchKeywordCmt", reviewDTO.getSearchKeywordCmt());
		}

        return mv;
    }
    
    // 리뷰 등록_장찬영
    @PostMapping("/insertReview") 
    public ResponseEntity<?> insertReview(@RequestParam("requestComment") String requestComment, 
    		@AuthenticationPrincipal CustomUserDetails customUser) throws JsonMappingException, JsonProcessingException {
    	Map<String, Object> Result = new ObjectMapper().readValue(requestComment, 
				new TypeReference<Map<String, Object>>() {});	
    	ResponseDTO<Map<String, Object>> response = new ResponseDTO<>();
    	
    	try {
    		Review reivew = Review.builder()
				    				.datecourseNo(Integer.valueOf((String)Result.get("datecourseNo")))
									.reviewComment(String.valueOf(Result.get("reviewComment")))
									.reviewerId(customUser.getUsername())
									.reviewRgstDate(LocalDateTime.now())
									.reviewModfDate(LocalDateTime.now())
									.build();

    		Review reviewResult = reviewService.insertReview(reivew);
    		
    		ReviewDTO returnReivew = ReviewDTO.builder()
    										.datecourseNo(reviewResult.getDatecourseNo())
    										.reviewNo(reviewResult.getReviewNo())
    										.reviewComment(reviewResult.getReviewComment())
    										.reviewerId(reviewResult.getReviewerId())
    										.reviewModfDate(reviewResult.getReviewModfDate().toString())
    										.build();
    		
    		Map<String, Object> returnMap = new HashMap<String, Object>(); 		
    		
    		returnMap.put("review", returnReivew);    		
    		response.setItem(returnMap);
			
			return ResponseEntity.ok().body(response);
    	}
    	catch(Exception e) {
    		response.setErrorMessage(e.getMessage());
			
			return ResponseEntity.badRequest().body(response);
    	}
    }
    
    // 리뷰 수정_장찬영
    @PutMapping("/updateReview")
    public /*ResponseEntity<?>*/ void updateReview(@RequestParam("updateReview") String updateReview, 
    		@AuthenticationPrincipal CustomUserDetails customUser) throws JsonMappingException, JsonProcessingException  {
    	Map<String, Object> update = new ObjectMapper().readValue(updateReview, 
				new TypeReference<Map<String, Object>>() {});
    	ResponseDTO<Map<String, Object>> response = new ResponseDTO<>();
    	
    	/*
    	try {
    		 Review review = Review.builder()
    				 				.datecourseNo(update.get("datecourseNo"))
    				 				.reviewNo(update.get("reviewNo"))
    				 				.reviewComment(update.get("reviewComment"))
    		 						.build();

    	}
    	catch(Exception e) {
    		response.setErrorMessage(e.getMessage());
			
			return ResponseEntity.badRequest().body(response);
    	}
    	*/
    	
    }
    
    // 관리자페이지 - 모달에서 리뷰 삭제 및 댓글 삭제_장찬영
    @DeleteMapping("/deleteReview")
    public void deleteReview(@RequestParam("result") String result) throws JsonMappingException, JsonProcessingException {
    	Map<String, Integer> resultById = new ObjectMapper().readValue(result, 
				new TypeReference<Map<String, Integer>>() {});
    	
    	System.out.println(resultById);
    	
    	ReviewId reviewId = new ReviewId();
    	reviewId.setDatecourseNo(resultById.get("datecourseNo"));
    	reviewId.setReviewNo(resultById.get("reviewNo"));
    	
    	
    	reviewService.deleteReview(reviewId);
    }
    
    // 관리자페이지 - 리뷰 리스트 삭제_장찬영
    @DeleteMapping("/deleteReviewList")
    public void deleteReviewList(@RequestParam("result") String result) throws JsonMappingException, JsonProcessingException {
    	List<Map<String, Integer>> resultList = new ObjectMapper().readValue(result, 
				new TypeReference<List<Map<String, Integer>>>() {});    	
    	List<Review> reviewListDel = new ArrayList<Review>(); 	
    	
    	for(int i = 0; i < resultList.size(); i++) {
    		Review reviewDel = Review.builder()
    							  .datecourseNo(resultList.get(i).get("datecourseNo"))
    							  .reviewNo(resultList.get(i).get("reviewNo"))
    							  .build();
    		
    		reviewListDel.add(reviewDel);
    	}
    	
    	reviewService.deleteReviewList(reviewListDel);
    	   	    	  	
    }
    
    // 관리자페이지 - 상세 리뷰(모달) 출력_장찬영
    @GetMapping("/reviewModal")
    public ResponseEntity<?> reviewModel(@RequestParam("requestModal") String requestModal) throws JsonMappingException, JsonProcessingException {
    	Map<String, Integer> modal = new ObjectMapper().readValue(requestModal, 
				new TypeReference<Map<String, Integer>>() {});
    	ResponseDTO<Map<String, Object>> response = new ResponseDTO<>();
    	
    	System.out.println("controller : " + modal);
    	 	
    	try {
    		Review review = Review.builder()
    							  .datecourseNo(modal.get("datecourseNo"))
    							  .reviewNo(modal.get("reviewNo"))
    							  .build();
    		
    		Review getModal = reviewService.reviewModel(review);
    		
    		System.out.println("Modal : " + getModal);
    		
    		ReviewDTO getModalDTO = ReviewDTO.builder()
    				.datecourseNo(getModal.getDatecourseNo())
    				.reviewNo(getModal.getReviewNo())
					.reviewerId(getModal.getReviewerId())
					.reviewComment(getModal.getReviewComment())
					.reviewModfDate(getModal.getReviewModfDate().toString())
					.build();
    		
    		Map<String, Object> returnMap = new HashMap<String, Object>(); 		
    		
    		returnMap.put("getModal", getModalDTO);
    		
    		response.setItem(returnMap);
			
			return ResponseEntity.ok().body(response);
    	}
    	catch(Exception e) {
    		response.setErrorMessage(e.getMessage());
			
			return ResponseEntity.badRequest().body(response);
    	}
    	

    }
    
}
