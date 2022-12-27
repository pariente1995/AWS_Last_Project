package com.gogi1000.datecourse.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/datecourse")
public class DatecourseController {

    // 데이트 코스 등록 화면으로 이동
    @RequestMapping("/insertDatecourse")
    public ModelAndView insertDatecourseView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("datecourse/insertDatecourse.html");

        return mv;
    }

    // 데이트 코스 등록
}