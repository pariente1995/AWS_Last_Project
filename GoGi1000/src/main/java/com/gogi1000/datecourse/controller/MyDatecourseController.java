package com.gogi1000.datecourse.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.dto.MyDatecourseDTO;
import com.gogi1000.datecourse.entity.CustomUserDetails;
import com.gogi1000.datecourse.entity.MyDatecourse;
import com.gogi1000.datecourse.entity.MyDatecourseId;
import com.gogi1000.datecourse.service.my.MyDatecourseService;

@RestController
@RequestMapping("/datecourse")
public class MyDatecourseController {
	@Autowired
	MyDatecourseService myDatecourseService;
	
	//마이페이지 내 데이트코스_김도원
	@GetMapping("/getMyDatecourseList")
	public ModelAndView myDatecourseView(MyDatecourseDTO myDatecourseDTO,
			@AuthenticationPrincipal CustomUserDetails customUser) {
	
		ModelAndView mv = new ModelAndView();
		
		MyDatecourse myDatecourse = MyDatecourse.builder()
												.userId(customUser.getUser().getUserId())
												.build();
													  
		List<CamelHashMap> DatecourseList = myDatecourseService.getMyDatecourseList(myDatecourse);

		mv.setViewName("datecourse/getMydatecourseList.html");
		mv.addObject("myDatecourseList", DatecourseList);
		System.out.println(myDatecourse);
		return mv;
				
	}
	//데이트코스 상세 페이지 내 데이트코스에 추가(버튼)_김도원
	@PostMapping("/insertMyDatecourse")
	public void insertMyDatecourse(@RequestParam("MyDateIns") String  myDateIns,
			@AuthenticationPrincipal CustomUserDetails customUser) throws JsonMappingException,
	JsonProcessingException {
		Map<String, Object> MyDateIns = new ObjectMapper().readValue(myDateIns, 
				new TypeReference<Map<String, Object>>(){});
	
		MyDatecourse myDatecourse = MyDatecourse.builder()
											   .datecourseNo(Integer.valueOf(MyDateIns.get("datecourseNo").toString()))
											   .userId(customUser.getUsername())
											   .myDatecourseRgstDate(LocalDateTime.now())
											   .build();
		
		myDatecourseService.insertMyDatecourse(myDatecourse);
	}
	
	//데이트코스 상세 페이지 내 데이트코스에서 삭제(버튼)_김도원
	@DeleteMapping("/deleteMyDatecourse")
	public void deleteMyDatecourse(@RequestParam("MyDateDel") String myDateDel,
			@AuthenticationPrincipal CustomUserDetails customUser) throws JsonMappingException,
	JsonProcessingException {
		Map<String, String> MyDateDel = new ObjectMapper().readValue(myDateDel, 
				new TypeReference<Map<String, String>>() {});
		
		System.err.println(MyDateDel);
		
		MyDatecourseId myDatecourseId = new MyDatecourseId();
		
		myDatecourseId.setDatecourseNo(Integer.valueOf(MyDateDel.get("datecourseNo")));
		myDatecourseId.setUserId(customUser.getUsername());
		
		myDatecourseService.deleteMyDatecourse(myDatecourseId);
	}
	
	//내 데이트코스 페이지에서 체크 박스 선택 후 삭제_김도원
    @DeleteMapping("/deleteMyDatecourseList")
    public void deleteMyDatecourseList(@RequestParam("MyDateDel") String MyDateDel, 
    		MyDatecourseDTO myDatecourseDTO, @AuthenticationPrincipal CustomUserDetails customUser) throws JsonMappingException, JsonProcessingException {
    	List<Map<String, String>> myDatecourseList  = new ObjectMapper().readValue(MyDateDel, 
				new TypeReference<List<Map<String, String>>>() {});    	
    	List<MyDatecourse> myDatecourseDelList  = new ArrayList<MyDatecourse>(); 	
    	
    	for(int i = 0; i < myDatecourseList.size(); i++) {
    		MyDatecourse myDatecourseDel = MyDatecourse.builder()
    							  					   .datecourseNo(Integer.valueOf(myDatecourseList.get(i).get("datecourseNo")))
    							  					   .userId(customUser.getUsername())
    							  					   .build();
    		
    		myDatecourseDelList.add(myDatecourseDel);
    	}
    	myDatecourseService.deleteMyDatecourseList(myDatecourseDelList);  	    	  	
    }
	
}

