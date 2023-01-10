package com.gogi1000.datecourse.service.user.impl;

import java.security.SecureRandom;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gogi1000.datecourse.entity.User;
import com.gogi1000.datecourse.repository.UserRepository;
import com.gogi1000.datecourse.service.mail.MailService;
import com.gogi1000.datecourse.service.user.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MailService mailService;

	@Override
	public void join(User user) {
		// TODO Auto-generated method stub
		userRepository.save(user);
	}
	
	@Override
	public User idCheck(User user) {
		// TODO Auto-generated method stub
		if(!userRepository.findById(user.getUserId()).isEmpty()) {
			return userRepository.findById(user.getUserId()).get();
		} else {
			return null;
		}
	}

	@Override
	public User findPwd(User user) {
		if(userRepository.findByUserIdAndUserMail(user.getUserId(), user.getUserMail()).isEmpty()) {
			return null;
		} else {
			return userRepository.findByUserIdAndUserMail(user.getUserId(), user.getUserMail()).get();
		}
	}

	@Override
	public void sendCert(User user) {
		// TODO Auto-generated method stub
		char[] charSet = new char[] 
	            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
	            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
	             'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 
	            'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 
	            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 
	            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '!', '@', '#', 
	            '$', '%', '^', '&' }; 
	      
	      StringBuffer sb = new StringBuffer();
	      SecureRandom sr = new SecureRandom(); 
	      sr.setSeed(new Date().getTime()); 
	      int idx = 0; int len = charSet.length; 
	      for (int i=0; i<10; i++) { 
	         idx = sr.nextInt(len); // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다. 
	         sb.append(charSet[idx]); 
	      }

	      String tempLoginPasswd = sb.toString();
	      
	      String mailTitle = user.getUserId() + "님, gogi1000 인증번호 입니다.";
	      String mailBody = "인증번호 : " + tempLoginPasswd;
	      mailService.send(user.getUserMail(), mailTitle, mailBody);


	      // 비밀번호 암호화해주는 메서드
	      tempLoginPasswd = passwordEncoder.encode(tempLoginPasswd);
	      
	      //비밀번호 바꾸기
	      userRepository.updateTempPw(user.getUserId(), tempLoginPasswd);

	}

}
