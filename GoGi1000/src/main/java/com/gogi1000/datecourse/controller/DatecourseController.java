package com.gogi1000.datecourse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/datecourse")
public class DatecourseController {

    // 데이트 코스 등록 화면으로 이동
    @GetMapping("/insertDatecourse")
    public ModelAndView insertDatecourseView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("datecourse/insertDatecourse.html");

        return mv;
    }

    // 데이트 코스 등록

    // 데이트 코스 수정 화면으로 이동
    @GetMapping("/updateDatecourse")
    public ModelAndView updateDatecourseView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("datecourse/updateDatecourse.html");

        return mv;
    }
    
}