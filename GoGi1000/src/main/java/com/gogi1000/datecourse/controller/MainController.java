package com.gogi1000.datecourse.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gogi1000.datecourse.dto.DatecourseDTO;
import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.service.main.MainService;

@RestController
@RequestMapping("/datecourse")
public class MainController {
	@Autowired
	private MainService mainService;
	
	
	
	@GetMapping("/datecourseList")
	public ModelAndView getDatecourseList(DatecourseDTO datecourseDTO) {
		
			Datecourse datecourse = Datecourse.builder()
					   .datecourseNm(datecourseDTO.getDatecourseNm())
					   .datecourseArea(datecourseDTO.getDatecourseArea())
					   .searchKeyword(datecourseDTO.getSearchKeyword())
					   .build();
			
			List<Datecourse> datecourseList = mainService.getDatecourseList(datecourse);
			
			
			List<DatecourseDTO> getDatecourseList = new ArrayList<DatecourseDTO>();
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
				getDatecourseList.add(returnDatecourse);
			}
			
			ModelAndView mv = new ModelAndView();
			// 뷰의 위치
			mv.setViewName("datecourse/datecourseList.html");
			
			if (datecourseDTO.getSearchKeyword() != null && !datecourseDTO.getSearchKeyword().equals("")) {
				mv.addObject("searchKeyword", datecourseDTO.getSearchKeyword());
			}
			// 뷰로 보낼 데이터 값
//			mv.addObject("getBoardList", pageBoardDTOList);
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
}
