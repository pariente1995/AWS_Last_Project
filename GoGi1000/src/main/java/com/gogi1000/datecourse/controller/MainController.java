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
	
	
	// 검색창에서 지역명, 코스명으로 검색 후 조회
	@GetMapping("/getSearchDatecourseList")
	public ModelAndView getSearchDatecourseList(DatecourseDTO datecourseDTO) {
		
			Datecourse datecourse = Datecourse.builder()
					   .searchKeyword(datecourseDTO.getSearchKeyword())
					   .build();
			
			List<Datecourse> searchDatecourseList = mainService.getSearchDatecourseList(datecourse);
			
			
			List<DatecourseDTO> getSearchDatecourseList = new ArrayList<DatecourseDTO>();
			for(int i = 0; i < searchDatecourseList.size(); i++) {
				DatecourseDTO returnDatecourse = DatecourseDTO.builder()
											   .datecourseNo(searchDatecourseList.get(i).getDatecourseNo())
											   .datecourseNm(searchDatecourseList.get(i).getDatecourseNm())
											   .datecourseSummary(searchDatecourseList.get(i).getDatecourseSummary())
											   .datecourseModfDate(
													   searchDatecourseList.get(i).getDatecourseModfDate() == null ?  
													    null : searchDatecourseList.get(i).getDatecourseModfDate().toString())
											   .datecourseUseYn(searchDatecourseList.get(i).getDatecourseUseYn())
											   .build();
				getSearchDatecourseList.add(returnDatecourse);
			}
			
			ModelAndView mv = new ModelAndView();
			// 뷰의 위치
			mv.setViewName("datecourse/getCateDatecourseList.html");
			
			if (datecourseDTO.getSearchKeyword() != null && !datecourseDTO.getSearchKeyword().equals("")) {
				mv.addObject("searchKeyword", datecourseDTO.getSearchKeyword());
			}
			// 뷰로 보낼 데이터 값
			mv.addObject("getSearchDatecourseList", getSearchDatecourseList);
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
	
	
	// 지도에서 지역 선택 후 조회
	@GetMapping("/getMapDatecourseList/{datecourseArea}")
	// key값 없이 value값만 던질때는 @PathVariable
	public ModelAndView getMapDatecourseList(@PathVariable String datecourseArea) throws IOException {	 
					
		// Map 받는 이유: 키와 값을 받아와야하기 때문에.
		// DB에서는 이름(컬럼)이 있고 값을 받아와야 하기 때문이고, 화면단에 NAME이 있고 KEY를 받아서 효과적으로 처리하기위해 map을 사용 
		List<CamelHashMap> mapDatecourseList = mainService.getMapDatecourseList(datecourseArea);
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("datecourse/getCateDatecourseList.html");
		mv.addObject("getMapDatecourseList", mapDatecourseList);
		
		return mv;
		
	}
	
	// 관리자 페이지 이동
	@GetMapping("/getDatecourseList")
	public ModelAndView getDatecourseList() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("datecourse/getDatecourseList.html");
	
		return mv;
	}
	
	
	// 메인 페이지에서 핫딜 리스트 조회
	
	
	// 메인 페이지에서 상세 페이지로 조회
	@GetMapping("/getHotdeal/{hotdealNo}")
	public ModelAndView getHotdeal(@PathVariable int hotdealNo) {
		
		Hotdeal hotdeal = mainService.getHotdeal(hotdealNo);
		
		HotdealDTO hodealDTO = HotdealDTO.builder()
							.hotdealNo(hotdeal.getHotdealNo())
							.hotdealNm(hotdeal.getHotdealNm())
							.hotdealDesc(hotdeal.getHotdealDesc())
							.hotdealPrice(hotdeal.getHotdealPrice())
							.hotdealSalerate(hotdeal.getHotdealSalerate())
							.hotdealTel(hotdeal.getHotdealTel())
							.hotdealEndDate(hotdeal.getHotdealEndDate())
							.hotdealRgstDate(hotdeal.getHotdealRgstDate() == null ?
									null : hotdeal.getHotdealRgstDate().toString())
							.build();
				
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hotdeal/getHotdeal.html");
		mv.addObject("getHotdeal", hodealDTO);	
		//mv.addObject("boardFileList", boardFileDTOList);
		
		return mv;
		
	}	
	
}
