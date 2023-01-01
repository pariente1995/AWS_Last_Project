package com.gogi1000.datecourse.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.dto.DatecourseDTO;
import com.gogi1000.datecourse.dto.HotdealDTO;
import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.entity.Hotdeal;
import com.gogi1000.datecourse.service.main.MainService;

@RestController
@RequestMapping("/main")
public class MainController {
	@Autowired
	private MainService mainService;
	
	
	// 데이트코스 카테고리별 리스트 조회
	@GetMapping("/getCateDatecourseList")
	public ModelAndView getCateDatecourseList(DatecourseDTO datecourseDTO) {
		
			Datecourse datecourse = Datecourse.builder()
					   .searchKeyword(datecourseDTO.getSearchKeyword())
					   .build();
			
			List<Datecourse> datecourseList = mainService.getCateDatecourseList(datecourse);
			
			
			List<DatecourseDTO> getCateDatecourseList = new ArrayList<DatecourseDTO>();
			for(int i = 0; i < datecourseList.size(); i++) {
				DatecourseDTO returnDatecourse = DatecourseDTO.builder()
											   .datecourseNo(datecourseList.get(i).getDatecourseNo())
											   .datecourseNm(datecourseList.get(i).getDatecourseNm())
											   .datecourseSummary(datecourseList.get(i).getDatecourseSummary())
											   .datecourseModfDate(
													   datecourseList.get(i).getDatecourseModfDate() == null ?  
													    null : datecourseList.get(i).getDatecourseModfDate().toString())
											   .datecourseUseYn(datecourseList.get(i).getDatecourseUseYn())
											   .build();
				getCateDatecourseList.add(returnDatecourse);
			}
			
			ModelAndView mv = new ModelAndView();
			// 뷰의 위치
			mv.setViewName("datecourse/getCateDatecourseList.html");
			
			if (datecourseDTO.getSearchKeyword() != null && !datecourseDTO.getSearchKeyword().equals("")) {
				mv.addObject("searchKeyword", datecourseDTO.getSearchKeyword());
			}
			// 뷰로 보낼 데이터 값
			mv.addObject("getCateDatecourseList", getCateDatecourseList);
//			
//			if (boardDTO.getSearchCondition() != null && !boardDTO.getSearchCondition().equals("")) {
//				mv.addObject("searchCondition", boardDTO.getSearchCondition());
//			}
//			
//			if (boardDTO.getSearchKeyword() != null && !boardDTO.getSearchKeyword().equals("")) {
//				mv.addObject("searchKeyword", boardDTO.getSearchKeyword());
//			}
			System.out.println(mv);
			return mv;
			
			
	}
	
	
	// 지도에서 지역 조회
	@GetMapping("/getCateDatecourseList/{datecourseArea}")
	// key값 없이 value값만 던질때는 @PathVariable
	public ModelAndView getCateDatecourseList(@PathVariable String datecourseArea) throws IOException {	 
					
		// Map 받는 이유: 키와 값을 받아와야하기 때문에.
		// DB에서는 이름(컬럼)이 있고 값을 받아와야 하기 때문이고, 화면단에 NAME이 있고 KEY를 받아서 효과적으로 처리하기위해 map을 사용 
		List<CamelHashMap> datecourseList = mainService.getCateDatecourseList(datecourseArea);
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("datecourse/getCateDatecourseList.html");
		mv.addObject("datecourseList", datecourseList);
		
		return mv;
		
	}
	
	// 관리자 페이지 이동
	@GetMapping("/getAdminDatecourseList")
	public ModelAndView getAdminDatecourseList() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("datecourse/getAdminDatecourseList.html");
	
		return mv;
	}
	
	// 핫딜로 이동
//	@GetMapping("getHotdeal")
//	public ModelAndView getHotdeal(HotdealDTO hotdealDTO) {
//		
//		Hotdeal hotdeal = Hotdeal.builder()
//					    	.hotdealNo(hotdealDTO.getHotdealNo())
//					    	.build();
//				
//	}	
	
}
