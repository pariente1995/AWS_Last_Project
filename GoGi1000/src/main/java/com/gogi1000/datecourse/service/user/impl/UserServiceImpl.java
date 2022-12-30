package com.gogi1000.datecourse.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogi1000.datecourse.entity.User;
import com.gogi1000.datecourse.repository.UserRepository;
import com.gogi1000.datecourse.service.user.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;

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

}
