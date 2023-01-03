package com.gogi1000.datecourse.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gogi1000.datecourse.common.FileUtils;
import com.gogi1000.datecourse.dto.DatecourseImageDTO;
import com.gogi1000.datecourse.dto.HotdealDTO;
import com.gogi1000.datecourse.dto.ResponseDTO;
import com.gogi1000.datecourse.entity.CustomUserDetails;
import com.gogi1000.datecourse.entity.DatecourseImage;
import com.gogi1000.datecourse.entity.Hotdeal;
import com.gogi1000.datecourse.service.hotdeal.HotdealService;

@RestController
@RequestMapping("/hotdeal")
public class HotdealController {
	@Autowired
	private HotdealService hotdealService;
    
	//핫딜 입력
	@GetMapping("/insertHotdeal")
	public ModelAndView insertHotdealView(@AuthenticationPrincipal CustomUserDetails customUser) throws IOException {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/insertHotdeal.html");
		
		return mv;
	}
	
	@PostMapping("/insertHotdeal")
	public void insertHotdeal(HotdealDTO hotdealDTO, MultipartFile[] uploadFiles, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
		Hotdeal hotdeal = Hotdeal.builder()
								 .hotdealNm(hotdealDTO.getHotdealNm())
								 .hotdealEndDate(hotdealDTO.getHotdealEndDate())
								 .hotdealOfficialSite(hotdealDTO.getHotdealOfficialSite())
								 .hotdealPrice(hotdealDTO.getHotdealPrice())
								 .hotdealSalerate(hotdealDTO.getHotdealSalerate())
								 .hotdealSeller(hotdealDTO.getHotdealSeller())
								 .hotdealSummary(hotdealDTO.getHotdealSummary())
								 .hotdealTel(hotdealDTO.getHotdealTel())
								 .hotdealDesc(hotdealDTO.getHotdealDesc())
								 .hotdealRgstDate(LocalDateTime.now())
								 .hotdealModfDate(LocalDateTime.now())
								 .hotdealUseYn("Y")
								 .build();
		
		
		List<DatecourseImage> uploadImageList = new ArrayList<DatecourseImage>();
		
		if(uploadFiles.length > 0) {
			String attachPath = request.getSession().getServletContext().getRealPath("/")
					+ "/upload";
			
			File directory = new File(attachPath);
			
			if(!directory.exists()) {
				directory.mkdir();
			}
		
			for(int i=0; i < uploadFiles.length; i++) {
				MultipartFile file = uploadFiles[i];
				
				if(!file.getOriginalFilename().equals("") &&
				file.getOriginalFilename() != null) {
					DatecourseImage datecourseImage = new DatecourseImage();
					
					datecourseImage = FileUtils.parseFileInfo(file, attachPath);
					uploadImageList.add(datecourseImage);
				}
			}
		}
		
		hotdealService.insertHotdeal(hotdeal, uploadImageList);
		
		response.sendRedirect("/hotdeal/insertHotdeal");
	}
	
	
	
    @GetMapping("/hotdealList")
    public ModelAndView getHotdealList(HotdealDTO hotdealDTO,
    		@PageableDefault(page=0, size=15) Pageable pageable) {
    	
    	Hotdeal hotdeal = Hotdeal.builder()
    							 .hotdealNm(hotdealDTO.getHotdealNm())
    							 .hotdealDesc(hotdealDTO.getHotdealDesc())
    							 .searchCondition(hotdealDTO.getSearchCondition())
    							 .searchKeyword(hotdealDTO.getSearchKeyword())
    							 .build();
    	
    	List<Hotdeal> hotdealList = hotdealService.getHotdealList(hotdeal);
    	
    	Page<Hotdeal> pageHotdealList = hotdealService.getPageHotdealList(hotdeal, pageable);
    	
    	Page<HotdealDTO> pageHotdealDTOList = pageHotdealList.map(pageHotdeal ->
    															  HotdealDTO.builder()
    															  			.hotdealNo(pageHotdeal.getHotdealNo())
    															  			.hotdealNm(pageHotdeal.getHotdealNm())
    															  			.hotdealOfficialSite(pageHotdeal.getHotdealOfficialSite())
    															  			.hotdealPrice(pageHotdeal.getHotdealPrice())
    						    											.hotdealSalerate(pageHotdeal.getHotdealSalerate())
    															  			.hotdealTel(pageHotdeal.getHotdealTel())
    															  			.hotdealDesc(pageHotdeal.getHotdealDesc())
    															  			.hotdealRgstDate(pageHotdeal.getHotdealRgstDate() == null ?
    															  					null : pageHotdeal.getHotdealRgstDate().toString())
    															  			.hotdealEndDate(pageHotdeal.getHotdealEndDate())
    															  			.hotdealModfDate(pageHotdeal.getHotdealModfDate() == null ?
    															  					null : pageHotdeal.getHotdealModfDate().toString())
    															  			.hotdealUseYn(pageHotdeal.getHotdealUseYn())
    															  			.build()
    															  			);
    	
    	List<HotdealDTO> getHotdealList = new ArrayList<HotdealDTO>();
    	
    	for(int i=0; i<hotdealList.size(); i++) {
    		HotdealDTO returnHotdeal = HotdealDTO.builder()
    											 .hotdealNo(hotdealList.get(i).getHotdealNo())
    											 .hotdealNm(hotdealList.get(i).getHotdealNm())
    											 .hotdealOfficialSite(hotdealList.get(i).getHotdealOfficialSite())
    											 .hotdealPrice(hotdealList.get(i).getHotdealPrice())
    											 .hotdealSalerate(hotdealList.get(i).getHotdealSalerate())
    											 .hotdealTel(hotdealList.get(i).getHotdealTel())
    											 .hotdealDesc(hotdealList.get(i).getHotdealDesc())
    											 .hotdealRgstDate(hotdealList.get(i).getHotdealRgstDate() == null ?
    													 null: hotdealList.get(i).getHotdealRgstDate().toString())
    											 .hotdealEndDate(hotdealList.get(i).getHotdealEndDate())
    											 .hotdealModfDate(hotdealList.get(i).getHotdealModfDate() == null ?
    													 null: hotdealList.get(i).getHotdealModfDate().toString())
    											 .hotdealUseYn(hotdealList.get(i).getHotdealUseYn())
    											 .build();
    		
    		getHotdealList.add(returnHotdeal);
    	}
    															  			
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("admin/getHotdealList.html");
    	mv.addObject("getHotdealList", pageHotdealDTOList);
    	
    	if(hotdealDTO.getSearchCondition() != null && !hotdealDTO.getSearchCondition().equals("")) {
    		mv.addObject("searchCondition", hotdealDTO.getSearchCondition());
    	}
    	if(hotdealDTO.getSearchKeyword() != null && !hotdealDTO.getSearchKeyword().equals("")) {
    		mv.addObject("searchKeyword", hotdealDTO.getSearchKeyword());
    	}
    	return mv;
    	
    }
    
    @RequestMapping("/updateHotdealUseYn")
    public ResponseEntity<?> updateHotdealUseYn(HotdealDTO hotdealDTO, Pageable pageable, HttpServletRequest request, HttpServletResponse response,
    		@RequestParam("valueArr") String valueArr) throws IOException{
    	
    	ResponseDTO<HotdealDTO> responseDTO = new ResponseDTO<HotdealDTO>();
    	try {
    
	    	List<Integer> valueArrList = new ObjectMapper().readValue(valueArr,
	    									new TypeReference<List<Integer>>() {});
	    	
	    	for(int i=0; i < valueArrList.size(); i++) {
	    		hotdealService.updateHotdealUseYn(valueArrList.get(i));
	    	}
	    	
	    	Hotdeal hotdeal = Hotdeal.builder()
	    							 .searchCondition(hotdealDTO.getSearchCondition())
	    							 .searchKeyword(hotdealDTO.getSearchKeyword())
	    							 .build();
	    	
	    	Page<Hotdeal> pageHotdealList = hotdealService.getPageHotdealList(hotdeal, pageable);
	    	
	    	Page<HotdealDTO> pageHotdealDTOList = pageHotdealList.map(pageHotdeal ->
																    	HotdealDTO.builder()
															  			.hotdealNo(pageHotdeal.getHotdealNo())
															  			.hotdealNm(pageHotdeal.getHotdealNm())
															  			.hotdealOfficialSite(pageHotdeal.getHotdealOfficialSite())
															  			.hotdealPrice(pageHotdeal.getHotdealPrice())
																		.hotdealSalerate(pageHotdeal.getHotdealSalerate())
															  			.hotdealTel(pageHotdeal.getHotdealTel())
															  			.hotdealDesc(pageHotdeal.getHotdealDesc())
															  			.hotdealRgstDate(pageHotdeal.getHotdealRgstDate() == null ?
															  					null : pageHotdeal.getHotdealRgstDate().toString())
															  			.hotdealEndDate(pageHotdeal.getHotdealEndDate())
															  			.hotdealModfDate(pageHotdeal.getHotdealModfDate() == null ?
															  					null : pageHotdeal.getHotdealModfDate().toString())
															  			.hotdealUseYn(pageHotdeal.getHotdealUseYn())
															  			.build()
															  			);
	    	responseDTO.setPageItems(pageHotdealDTOList);
	    	return ResponseEntity.ok().body(responseDTO);
	    	
    	} catch(Exception e) {
    		responseDTO.setErrorMessage(e.getMessage());
    		
    		return ResponseEntity.badRequest().body(responseDTO);
    	}
    }
    @GetMapping("getHotdeal/{hotdealNo}")
    public ModelAndView getHotdeal(@PathVariable int hotdealNo ) {
    	Hotdeal hotdeal = hotdealService.getHotdeal(hotdealNo);
    	
    	HotdealDTO hotdealDTO = HotdealDTO.builder()
    									  .hotdealNo(hotdeal.getHotdealNo())
    									  .hotdealNm(hotdeal.getHotdealNm())
    									  .hotdealSummary(hotdeal.getHotdealSummary())
    									  .hotdealEndDate(hotdeal.getHotdealEndDate())
    									  .hotdealSalerate(hotdeal.getHotdealSalerate())
    									  .hotdealSeller(hotdeal.getHotdealSeller())
    									  .hotdealTel(hotdeal.getHotdealTel())
    									  .hotdealPrice(hotdeal.getHotdealPrice())
    									  .hotdealOfficialSite(hotdeal.getHotdealOfficialSite())
    									  .hotdealDesc(hotdeal.getHotdealDesc())
    									  .hotdealUseYn(hotdeal.getHotdealUseYn())
    									  .build();
    	
    	List<DatecourseImage> datecourseImageList = hotdealService.getHotdealImageList(hotdealNo);
    	
    	List<DatecourseImageDTO> datecourseImageDTOList = new ArrayList<>();
    	
    	for(DatecourseImage datecourseImage : datecourseImageList) {
    		DatecourseImageDTO datecourseImageDTO = DatecourseImageDTO.builder()
    																  .imageGroup(datecourseImage.getImageGroup())
    																  .referenceNo(datecourseImage.getReferenceNo())
    																  .imageNo(datecourseImage.getImageNo())
    																  .imageNm(datecourseImage.getImageNm())
    																  .imageOriginNm(datecourseImage.getImageOriginNm())
    																  .imageExt(datecourseImage.getImageExt())
    																  .imagePath(datecourseImage.getImagePath())
    																  .build();
    		datecourseImageDTOList.add(datecourseImageDTO);				  
    	}
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("admin/getHotdeal.html");
    	mv.addObject("getHotdeal", hotdealDTO);
    	mv.addObject("getDatecourseImageList", datecourseImageDTOList);
    	
 		return mv;			  
    	}
    
    
	
	@GetMapping("/updateHotdeal")
	public ModelAndView updateHotdeal() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("admin/updateHotdeal.html");
		
		return mv;
	}
}
