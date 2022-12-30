package com.gogi1000.datecourse.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gogi1000.datecourse.entity.CustomUserDetails;
import com.gogi1000.datecourse.entity.User;
import com.gogi1000.datecourse.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(userRepository.findById(username).isEmpty()) {
			return null;
		} else {
			User user = userRepository.findById(username).get();
			
			return CustomUserDetails.builder()
									.user(user)
									.build();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
