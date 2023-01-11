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
import com.gogi1000.datecourse.dto.ReviewDTO;
import com.gogi1000.datecourse.entity.CustomUserDetails;
import com.gogi1000.datecourse.entity.Like;
import com.gogi1000.datecourse.entity.LikeId;
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
        
        ReviewDTO reviewDTO = ReviewDTO.builder()
										.reviewerId(customUser.getUsername())
										.build();
        
        Page<CamelHashMap> likeList = likeService.mypageLikeList(reviewDTO, pageable);
        
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
    public void deleteLike(@RequestParam("likeDel") String likeDel,
    		@AuthenticationPrincipal CustomUserDetails customUser) throws JsonMappingException, JsonProcessingException {
    	Map<String, Object> like = new ObjectMapper().readValue(likeDel, 
				new TypeReference<Map<String, Object>>() {});
    	
    	LikeId likeId = new LikeId();
    	
    	likeId.setDatecourseNo(Integer.valueOf((String)like.get("datecourseNo")));
    	likeId.setUserId(customUser.getUsername());
        
    	likeService.deleteLike(likeId);       
    }
    
    // 좋아요 등록_장찬영
    @PostMapping("/InsertLike")
    public void InsertLike(@RequestParam("likeIns") String likeIns,
    		@AuthenticationPrincipal CustomUserDetails customUser) throws JsonMappingException, JsonProcessingException {
    	Map<String, Object> likeInt = new ObjectMapper().readValue(likeIns, 
				new TypeReference<Map<String, Object>>() {});
    	
    	Like like = Like.builder()
    					.datecourseNo(Integer.valueOf((String)likeInt.get("datecourseNo")))
    					.userId(customUser.getUsername())
    					.likeYn("Y")
    					.build();
    	
    	likeService.insertLike(like);
    }
}
