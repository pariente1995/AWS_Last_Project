package com.gogi1000.datecourse.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.dto.LikeDTO;
import com.gogi1000.datecourse.entity.CustomUserDetails;
import com.gogi1000.datecourse.entity.Like;
import com.gogi1000.datecourse.entity.LikeId;
import com.gogi1000.datecourse.entity.Review;
import com.gogi1000.datecourse.service.like.LikeService;

@RestController
@RequestMapping("/like")
public class LikeController {
	@Autowired
	LikeService likeService;
	
	// 마이페이지 - 좋아요 리스트 출력_장찬영
    @GetMapping("/mypageLikeList")
    public ModelAndView mypageLikeListView(@PageableDefault(page=0, size=7) Pageable pageable,
    		@AuthenticationPrincipal CustomUserDetails customUser) {
        ModelAndView mv = new ModelAndView();
        
        Review review = Review.builder()
								.reviewerId(customUser.getUsername())
								.build();
        
        Page<CamelHashMap> likeList = likeService.mypageLikeList(review, pageable);
        
        System.out.println(likeList.getPageable().toString());
        System.out.println("=======================================");
        System.out.println(likeList.getContent().toString());
        System.out.println("=======================================");
        System.out.println(likeList.getTotalElements());
        
        mv.setViewName("like/mypageLikeList.html");
        mv.addObject("likeList", likeList);

        return mv;
    }
    
    // 좋아요 삭제_장찬영
    @DeleteMapping("/deleteLike")
    public void deleteLike(LikeDTO likeDTO,
    		@AuthenticationPrincipal CustomUserDetails customUser) throws JsonMappingException, JsonProcessingException {
    	
    	Like like = Like.builder()
				.datecourseNo(likeDTO.getDatecourseNo())
				.userId(customUser.getUsername())
				.build();
    	       
    	likeService.deleteLike(like);       
    }
    
    // 좋아요 등록_장찬영
    @PostMapping("/InsertLike")
    public void InsertLike(LikeDTO likeDTO,
    		@AuthenticationPrincipal CustomUserDetails customUser) throws JsonMappingException, JsonProcessingException {   		
    	
    	Like like = Like.builder()
    					.datecourseNo(likeDTO.getDatecourseNo())
    					.userId(customUser.getUsername())
    					.likeYn("Y")
    					.build();
    	
    	likeService.insertLike(like);
    }
}
