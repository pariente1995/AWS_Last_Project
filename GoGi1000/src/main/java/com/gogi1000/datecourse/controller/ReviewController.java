package com.gogi1000.datecourse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/review")
public class ReviewController {
	
<<<<<<< HEAD
	// 관리자페이지 - 댓글리스트 화면으로 이동
    @GetMapping("/adminpageComment")
    public ModelAndView insertDatecourseView() {
=======
	// 관리자페이지 - 댓글 리스트 화면으로 이동
    @GetMapping("/adminpageComment")
    public ModelAndView mypageLikeListView() {
>>>>>>> f0beed71f5ec66bde7e279666f49e564853fe599
        ModelAndView mv = new ModelAndView();
        mv.setViewName("review/adminpageComment.html");

        return mv;
    }
}
