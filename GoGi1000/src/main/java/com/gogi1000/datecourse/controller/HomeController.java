package com.gogi1000.datecourse.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/home")
public class HomeController {
	// 서버 실행 시, main.html 화면으로 이동
    @RequestMapping("/main")
    public ModelAndView mainPage() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("main.html");

        return mv;
    }
}