package com.gogi1000.datecourse.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/join")
	public ModelAndView joinView() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("user/join.html");
		return mv;
	}
	
	@PostMapping("/join")
	public ResponseEntity<?> join(UserDTO userDTO) {
		System.out.println("userDTO " + userDTO);
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {			
			User user = User.builder()
							.userId(userDTO.getUserId())
							.userPw(passwordEncoder.encode(userDTO.getUserPw()))
							.userNm(userDTO.getUserNm())
							.userAge(userDTO.getUserAge())
							.userTel(userDTO.getUserTel())
							.userMail(userDTO.getUserMail())
							.userArea(userDTO.getUserArea())
							.userAddr1(userDTO.getUserAddr1())
							.userAddr2(userDTO.getUserAddr2())
							.userType("ROLE_USER")
							.userRgstDate(LocalDateTime.now())
							.userModfDate(LocalDateTime.now())
							.userUseYn("Y")
							.build();
			
			User returnUser = userService.join(user);
			
			if(returnUser != null) {
				returnMap.put("msg", "ok");
			} else {
				returnMap.put("msg", "fail");
			}
			
			responseDTO.setItem(returnMap);

			return ResponseEntity.ok().body(responseDTO);
		} catch(Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
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
											.userAge(checkedUser.getUserAge())
											.userTel(checkedUser.getUserTel())
											.userMail(checkedUser.getUserMail())
											.userArea(checkedUser.getUserArea())
											.userAddr1(checkedUser.getUserAddr1())
											.userAddr2(checkedUser.getUserAddr2())
											.userType(checkedUser.getUserType())
											.userRgstDate(checkedUser.getUserRgstDate().toString())
											.userModfDate(checkedUser.getUserModfDate().toString())
											.userUseYn(checkedUser.getUserUseYn())
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
	
	@GetMapping("/findId")
	public ModelAndView findIdView() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("user/findId.html");
		return mv;
	}
	
	@PostMapping("/findId")
	public ResponseEntity<?> checkId(UserDTO userDTO) {
		ResponseDTO<Map<String, String>> response = new ResponseDTO<>();
		
		try {
			User user = User.builder()
							.userNm(userDTO.getUserNm())
							.userMail(userDTO.getUserMail())
							.build();
			int userNmCnt = userService.getUserNmCnt(user);
			
			Map<String, String> returnMap = new HashMap<String, String>();
			
			if(userNmCnt <= 0) {
				returnMap.put("findIdMsg", "wrongNm");
			}  else {
				User chkUser = userService.findId(user);
				
				if(chkUser != null) {
					returnMap.put("findIdMsg", "infoOK");
					returnMap.put("userId", chkUser.getUserId());
					returnMap.put("userRgstDate", chkUser.getUserRgstDate().toString());
				} else {
					returnMap.put("findIdMsg", "wrongMail");
				}
			}
			
			response.setItem(returnMap);
			
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PostMapping("/checkId")
	public ModelAndView checkIdView(UserDTO userDTO) {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("user/checkId.html");
		mv.addObject("user", userDTO);
		return mv;
	}
	
	
	
	@GetMapping("/findPwd")
	public ModelAndView findPwdView() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("user/findPwd.html");
		return mv;
	}
	
	@PostMapping("/findPwd")
	public ResponseEntity<?> findPwd(UserDTO userDTO) {
		ResponseDTO<Map<String, String>> response = new ResponseDTO<>();
		
		try {
			User user = User.builder()
							.userId(userDTO.getUserId())
							.userMail(userDTO.getUserMail())
							.build();
			
			User chkUser = userService.findPwd(user);
			
			Map<String, String> returnMap = new HashMap<String, String>();
			
			if(userService.idCheck(user) == null) {
				returnMap.put("findPwdMsg", "wrongId");
			} else if(chkUser == null) {
				returnMap.put("findPwdMsg", "wrongMail");
			} else {
				userService.sendCert(user); // 인증번호 생성 부분 + 메일 전송
				returnMap.put("findPwdMsg", "sendCefrt");
			}
			
			response.setItem(returnMap);
			
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
			response.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	//입력한 인증번호랑 비밀번호랑 비교(passwordEncoder.matches(1: 입력한 비밀번호(암호화 되지않은 비밀번호), 2: db 저장된 암호(암호화된 비밀번호))
	
	@PostMapping("/pwCheck")
	public ResponseEntity<?> newPwd(UserDTO userDTO) {
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {
			User user = User.builder()
							.userId(userDTO.getUserId())
							.build();
			
			User checkedUser = userService.pwCheck(user);
			
			if(!passwordEncoder.matches(userDTO.getUserPw(), checkedUser.getUserPw())) {
				returnMap.put("msg", "pwFail");
			} else {
				returnMap.put("msg", "pwOK");
			}
			
			responseDTO.setItem(returnMap);
			
			return ResponseEntity.ok().body(responseDTO);
		} catch(Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@GetMapping("/newPwd")
	public ModelAndView newPwdView(UserDTO userDTO) {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("user/newPwd.html");
		mv.addObject("user", userDTO);
		return mv;
	}
	
	@PostMapping("/chPw")
	public ResponseEntity<?> chPw(UserDTO userDTO) {
		ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();
		Map<String, String> returnMap = new HashMap<String, String>();
		
		try {
			User user = User.builder()
							.userId(userDTO.getUserId())
							.userPw(passwordEncoder.encode(userDTO.getUserPw()))
							.build();
			
			//User checkedUser = userService.pwCheck(user);
			
			int chPw = userService.updateUserPw(user);
			
			if(chPw == 0) {
				returnMap.put("msg", "newPwFail");
			} else {
				returnMap.put("msg", "newPwOK");
			}
			
			responseDTO.setItem(returnMap);
			
			return ResponseEntity.ok().body(responseDTO);
		} catch(Exception e) {
			responseDTO.setErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	@GetMapping("/editMyinfo")
	public ModelAndView editMyinfoView() {
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("user/editMyinfo.html");
		return mv;
	}
}
