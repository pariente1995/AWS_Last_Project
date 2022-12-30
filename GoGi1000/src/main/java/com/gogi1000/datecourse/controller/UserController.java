package com.gogi1000.datecourse.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gogi1000.datecourse.dto.ResponseDTO;
import com.gogi1000.datecourse.dto.UserDTO;
import com.gogi1000.datecourse.entity.User;
import com.gogi1000.datecourse.service.user.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/join")
	public ModelAndView joinView() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("user/join.html");
		return mv;
	}
	
	@PostMapping("/join")
	public ResponseEntity<?> join(UserDTO userDTO) {
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		try {
			//User user = new User();
			//user.setUserId(userDTO.getUserId());
			//user.setUserPw(userDTO.getUserPw());
			//user.setUserNm(userDTO.getUserNm());
			//user.setUserMail(userDTO.getUserMail());
			//user.setUserId(userDTO.getUserId());
			
			User user = User.builder()
							.userId(userDTO.getUserId())
							.userPw(userDTO.getUserPw())
							.userNm(userDTO.getUserNm())
							.userTel(userDTO.getUserTel())
							.userMail(userDTO.getUserMail())
							.userArea(userDTO.getUserTel())
							.userAddr1(userDTO.getUserAddr1())
							.userAddr2(userDTO.getUserAddr2())
							.userType(userDTO.getUserType())
							.userRgstdate(LocalDateTime.now())
							.userModfdate(LocalDateTime.now())
							.userUseyn(userDTO.getUserUseYn())
							.build();
			
			userService.join(user);
			
			returnMap.put("joinMsg", "joinSuccess");
			
			responseDTO.setItem(returnMap);
			
			return ResponseEntity.ok().body(responseDTO);
		} catch(Exception e) {
			returnMap.put("joinMsg", "joinFail");
			responseDTO.setErrorMessage(e.getMessage());
			responseDTO.setItem(returnMap);
			
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@GetMapping("/login")
	public ModelAndView loginView() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("user/login.html");
		return mv;
	}
	
	@PostMapping("/idCheck")
	public ResponseEntity<?> idCheck(UserDTO userDTO) {
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {
			User user = User.builder()
							.userId(userDTO.getUserId())
							.build();
			
			User checkedUser = userService.idCheck(user);
			
			if(checkedUser != null) {
				returnMap.put("msg", "duplicatedId");
			} else {
				returnMap.put("msg", "idOk");
			}
			
			responseDTO.setItem(returnMap);
			
			return ResponseEntity.ok().body(responseDTO);
		} catch(Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(UserDTO userDTO) {
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {
			User user = User.builder()
							.userId(userDTO.getUserId())
							.userPw(userDTO.getUserPw())
							.build();
			
			User checkedUser = userService.idCheck(user);
			
			if(checkedUser == null) {
				returnMap.put("msg", "idFail");
			} else {
				//User loginUser = userService.login(user);
				
				if(!checkedUser.getUserPw().equals(userDTO.getUserPw())) {
					returnMap.put("msg", "pwFail");
				} else {
					UserDTO loginUser = UserDTO.builder()
											.userId(checkedUser.getUserId())
											.userPw(checkedUser.getUserPw())
											.userNm(checkedUser.getUserNm())
											.userTel(checkedUser.getUserTel())
											.userMail(checkedUser.getUserMail())
											.userArea(checkedUser.getUserTel())
											.userAddr1(checkedUser.getUserAddr1())
											.userAddr2(checkedUser.getUserAddr2())
											.userType(checkedUser.getUserType())
											.userRgstDate(checkedUser.getUserRgstdate().toString())
											.userModfDate(checkedUser.getUserModfdate().toString())
											.userUseYn(checkedUser.getUserUseyn())
											.build();
					
					returnMap.put("msg", "loginSuccess");
				}
			}
			
			responseDTO.setItem(returnMap);
			
			return ResponseEntity.ok().body(responseDTO);
		} catch(Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
}
