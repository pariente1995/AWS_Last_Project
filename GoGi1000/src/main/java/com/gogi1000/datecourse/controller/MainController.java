package com.gogi1000.datecourse.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.dto.DatecourseDTO;
import com.gogi1000.datecourse.dto.DatecourseImageDTO;
import com.gogi1000.datecourse.dto.HotdealDTO;
import com.gogi1000.datecourse.dto.ResponseDTO;
import com.gogi1000.datecourse.dto.ReviewDTO;
import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.entity.DatecourseImage;
import com.gogi1000.datecourse.entity.Hotdeal;
import com.gogi1000.datecourse.entity.Review;
import com.gogi1000.datecourse.service.main.MainService;

@RestController
@RequestMapping("/main")
public class MainController {
	@Autowired
	private MainService mainService;
	
	
	// 검색창에서 지역명, 코스명, 내용으로 검색 후 조회
	@GetMapping("/getSearchMapDatecourseList")
	public ModelAndView getSearchMapDatecourseList(
			DatecourseDTO datecourseDTO,
		@PageableDefault(page=0, size=12) Pageable pageable)  throws IOException  {
		Datecourse datecourse = Datecourse.builder()
									   .datecourseArea(datecourseDTO.getDatecourseArea())
									   .searchKeyword(datecourseDTO.getSearchKeyword())
									   .build();
		
		Page<CamelHashMap> searchMapDatecourseList = mainService.getSearchMapDatecourseList(datecourse, pageable);
		
		
		ModelAndView mv = new ModelAndView();
		
		// 뷰의 위치
		mv.setViewName("datecourse/getCateDatecourseList.html");
		
		if(datecourseDTO.getSearchKeyword() != null && !datecourseDTO.getSearchKeyword().equals("")) {
			mv.addObject("searchKeyword", datecourseDTO.getSearchKeyword());
		} 
		
		if(datecourseDTO.getDatecourseArea() != null && !datecourseDTO.getDatecourseArea().equals("")) {
			mv.addObject("datecourseArea", datecourseDTO.getDatecourseArea());
		}
		
		mv.addObject("searchMapDatecourseList", searchMapDatecourseList);
		
		return mv;
			
	}
	
	// 메인에서 관리자 페이지 이동
	@GetMapping("/getDatecourseList")
	public ModelAndView getDatecourseList() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("admin/getDatecourseList.html");
	
		return mv;
	}
	
	// 메인에서 인기 상세 페이지 조회 시, 조회수 증가_인겸
	@GetMapping("/updateCateDatecourseCnt/{datecourseNo}")
	public void updateBoardCnt(@PathVariable int datecourseNo, HttpServletResponse response) throws IOException {
		mainService.updateCateDatecourseCnt(datecourseNo);
		
		response.sendRedirect("/main/getCateDatecourse/" + datecourseNo);
	}
	
	// 메인 페이지에서 인기 상세 페이지 조회_인겸
	@GetMapping("/getCateDatecourse/{datecourseNo}")
	public ModelAndView getCateDatecourse(@PathVariable int datecourseNo) {
		Datecourse datecourse = mainService.getCateDatecourse(datecourseNo);
		
		DatecourseDTO datecourseDTO = DatecourseDTO.builder()
													.datecourseNo(datecourse.getDatecourseNo())
													.datecourseNm(datecourse.getDatecourseNm())
													.datecourseDesc(datecourse.getDatecourseDesc())
													.datecourseAddr(datecourse.getDatecourseAddr())
													.datecourseTel(datecourse.getDatecourseTel())
													.datecourseOfficialSite(datecourse.getDatecourseOfficialSite())
													.datecourseParkingYn(datecourse.getDatecourseParkingYn())
													.build();
				
		List<CamelHashMap> datecourseHours = mainService.getCateDatecourseHours(datecourseNo);
		
		List<CamelHashMap> datecourseMenu = mainService.getCateDatecourseMenu(datecourseNo); 
		
		
		
		List<DatecourseImage> datecourseImageList = mainService.getDatecourseImageList(datecourseNo);
		
		List<DatecourseImageDTO> datecourseImageDTOList = new ArrayList<DatecourseImageDTO>();
		
		// DTO에 담아서 다시 던져줌
		for (DatecourseImage datecourseImage : datecourseImageList) {
			DatecourseImageDTO datecourseImageDTO = DatecourseImageDTO.builder()
													.referenceNo(datecourseNo)
													.imageNo(datecourseImage.getImageNo())
													.imageNm(datecourseImage.getImageNm())
													.imageOriginNm(datecourseImage.getImageOriginNm())
													.imagePath(datecourseImage.getImagePath())
													.imageRgstDate(datecourseImage.getImageRgstDate().toString())
													.imageGroup(datecourseImage.getImageGroup())
													.build();
			
			datecourseImageDTOList.add(datecourseImageDTO);
		}
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("datecourse/getCateDatecourse.html");
		
		mv.addObject("datecourse", datecourseDTO);
		
		mv.addObject("datecourseImageDTOList", datecourseImageDTOList);
		
		mv.addObject("datecourseMenu", datecourseMenu);
		
		mv.addObject("datecourseHours", datecourseHours);
		
		return mv;
				
	}
	
	// 메인 페이지에서 핫딜 상세 페이지로 조회
	@GetMapping("/getHotdeal/{hotdealNo}")
	public ModelAndView getHotdeal(@PathVariable int hotdealNo) {
		
		Hotdeal hotdeal = mainService.getHotdeal(hotdealNo);
		
		HotdealDTO hodealDTO = HotdealDTO.builder()
							.hotdealNo(hotdeal.getHotdealNo())
							.hotdealNm(hotdeal.getHotdealNm())
							.hotdealDesc(hotdeal.getHotdealDesc())
							.hotdealPrice(hotdeal.getHotdealPrice())
							.hotdealOfficialSite(hotdeal.getHotdealOfficialSite())
							.hotdealSeller(hotdeal.getHotdealSeller())
							.hotdealSalerate(hotdeal.getHotdealSalerate())
							.hotdealTel(hotdeal.getHotdealTel())
							.hotdealEndDate(hotdeal.getHotdealEndDate())
							.hotdealRgstDate(hotdeal.getHotdealRgstDate() == null ?
									null : hotdeal.getHotdealRgstDate().toString())
							.build();
		
		// 이미지 조회하기
		List<DatecourseImage> datecourseImageList = mainService.getDatecourseHotdealImageList(hotdealNo);
		
		List<DatecourseImageDTO> getDatecourseImageList = new ArrayList<DatecourseImageDTO>();
		
		// DTO에 담아서 다시 던져줌
		for (DatecourseImage datecourseImage : datecourseImageList) {
			DatecourseImageDTO datecourseImageDTO = DatecourseImageDTO.builder()
													.referenceNo(hotdealNo)
													.imageNo(datecourseImage.getImageNo())
													.imageNm(datecourseImage.getImageNm())
													.imageOriginNm(datecourseImage.getImageOriginNm())
													.imagePath(datecourseImage.getImagePath())
													.imageRgstDate(datecourseImage.getImageRgstDate().toString())
													.imageGroup(datecourseImage.getImageGroup())
													.build();
			
			getDatecourseImageList.add(datecourseImageDTO);
		}
				
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hotdeal/getHotdealDetail.html");
		mv.addObject("getHotdeal", hodealDTO);	
		mv.addObject("getDatecourseImageList", getDatecourseImageList);
		
		
		return mv;
		
	}		
	
	
}
