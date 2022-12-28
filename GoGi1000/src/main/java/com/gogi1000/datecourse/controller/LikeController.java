package com.gogi1000.datecourse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/like")
public class LikeController {
	
	// 마이페이지 - 좋아요리스트 화면으로 이동
    @GetMapping("/mypageLikeList")
    public ModelAndView insertDatecourseView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("like/mypageLikeList.html");

        return mv;
    }
}
