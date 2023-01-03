package com.gogi1000.datecourse.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.dto.DatecourseDTO;
import com.gogi1000.datecourse.dto.HotdealDTO;
import com.gogi1000.datecourse.entity.Hotdeal;
import com.gogi1000.datecourse.service.main.MainService;

@RestController
@RequestMapping("/home")
public class HomeController {
	@Autowired
	private MainService mainService;
	
	
	// 서버 실행 시, main.html 화면으로 이동
    @RequestMapping("/main")
    public ModelAndView mainPage() {
    	// 메인 화면에서 인기 리스트 조회
    	
    	// CamelHashMap 사용: entity를 사용할 땐 entity의 내용이 변경되거나, 삭제됐을 때 DB에 반영될 수 있기 떄문에, 
    	//				 	DTO로 변환해주는 작업을 했었는데, CamelHashMap을 사용했을 때는, DB에 접근할 일이 없기 떄문에
    	//					바로 화면단으로 DTO 변환 작업없이 보내줘도 된다.
    	List<CamelHashMap> rankDatecourseList = mainService.getRankDatecourseList();
		
    	// 메인 화면에서 핫딜 리스트 조회
		List<CamelHashMap> hotdealDatecourseList = mainService.getHotdealDatecourseList();
		
    	
        ModelAndView mv = new ModelAndView();

        mv.setViewName("main.html");
        mv.addObject("getRankDatecourseList", rankDatecourseList);
        mv.addObject("getHotdealDatecourseList", hotdealDatecourseList);

        return mv;
    }
}