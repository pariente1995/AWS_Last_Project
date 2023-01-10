package com.gogi1000.datecourse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gogi1000.datecourse.dto.DatecourseDTO;
import com.gogi1000.datecourse.dto.MyDatecourseDTO;
import com.gogi1000.datecourse.dto.ReviewDTO;
import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.entity.MyDatecourse;
import com.gogi1000.datecourse.entity.MyDatecourseId;
import com.gogi1000.datecourse.entity.Review;
import com.gogi1000.datecourse.service.my.MyDatecourseService;

@RestController
@RequestMapping("/datecourse")
public class MyDatecourseController {
	@Autowired
	MyDatecourseService myDatecourseService;
	
	
	@GetMapping("/getMyDatecourseList")
	public ModelAndView myDatecourseView(MyDatecourseDTO myDatecourseDTO) {
	
		ModelAndView mv = new ModelAndView();
		
		MyDatecourse myDatecourse = MyDatecourse.builder()
									       		.datecourseNo(myDatecourseDTO.getDatecourseNo())
												.userId(myDatecourseDTO.getUserId())
												.build();
		
		List<Datecourse> DatecourseList = myDatecourseService.getMyDatecourseList(myDatecourse);
		
		List<DatecourseDTO> myDatecourseDTOList = new ArrayList<DatecourseDTO>();
		
		for(int i=0; i < DatecourseList.size(); i++) {
			DatecourseDTO returnDatecourse = DatecourseDTO.builder()
					  									  .datecourseNo(DatecourseList.get(i).getDatecourseNo())
					  									  .datecourseNm(DatecourseList.get(i).getDatecourseNm())
					  									  .datecourseAddr(DatecourseList.get(i).getDatecourseAddr())
					  									  .build();
			
			myDatecourseDTOList.add(returnDatecourse);
		}
		
		mv.setViewName("/admin/mydatecourse.html");
		mv.addObject("myDatecourse", DatecourseList);
		mv.addObject("myDatecourseList", myDatecourseDTOList);
		
		return mv;
				
	}
	@PostMapping("/insertMyDatecourse")
	public void insertMyDatecourse(@RequestParam("MyDateIns") String  myDateIns) throws JsonMappingException,
	JsonProcessingException {
		Map<String, String> MyDateIns = new ObjectMapper().readValue(myDateIns, 
				new TypeReference<Map<String, String>>(){});
	
		MyDatecourse myDatecourse = MyDatecourse.builder()
											   .datecourseNo(Integer.valueOf(MyDateIns.get("getDatecourseNo")))
											   .userId(MyDateIns.get("userId"))
											   .build();
		
		myDatecourseService.insertMyDatecourse(myDatecourse);
	}
	
	@DeleteMapping("/deleteMyDatecourse")
	public void deleteMyDatecourse(@RequestParam("MyDateDel") String myDateDel) throws JsonMappingException,
	JsonProcessingException {
		Map<String, String> MyDateDel = new ObjectMapper().readValue(myDateDel, 
				new TypeReference<Map<String, String>>() {});
		
		MyDatecourseId myDatecourseId = new MyDatecourseId();
		
		myDatecourseId.setDatecourseNo(Integer.valueOf(MyDateDel.get("datecourseNo")));
		myDatecourseId.setUserId(MyDateDel.get("userId"));
		
		myDatecourseService.deleteMyDatecourse(myDatecourseId);
	}
	
    @DeleteMapping("/deleteMyDatecourseList")
    public void deleteMyDatecourseList(@RequestParam("MyDateDel") String myDateDel, MyDatecourseDTO myDatecourseDTO) throws JsonMappingException, JsonProcessingException {
    	List<Map<String, Integer>> myDatecourseList  = new ObjectMapper().readValue(myDateDel, 
				new TypeReference<List<Map<String, Integer>>>() {});    	
    	List<MyDatecourse> myDatecourseDelList  = new ArrayList<MyDatecourse>(); 	
    	
    	for(int i = 0; i < myDatecourseList.size(); i++) {
    		MyDatecourse myDatecourseDel = MyDatecourse.builder()
    							  					   .datecourseNo(myDatecourseList.get(i).get("datecourseNo"))
    							  					   .userId(myDatecourseList.get(i).get("userId").toString())
    							  					   .build();
    		
    		myDatecourseDelList.add(myDatecourseDel);
    	}
    	myDatecourseService.deleteMyDatecourseList(myDatecourseDelList);  	    	  	
    }
	
}
