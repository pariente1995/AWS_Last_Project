package com.gogi1000.datecourse.service.my.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.MyDatecourse;
import com.gogi1000.datecourse.entity.MyDatecourseId;
import com.gogi1000.datecourse.repository.MyDatecourseRepository;
import com.gogi1000.datecourse.service.my.MyDatecourseService;

@Service
public class MyDatecourseServiceImpl implements MyDatecourseService {
	
	@Autowired
	MyDatecourseRepository myDatecourseRepository;
	

	@Override
	public List<CamelHashMap> getMyDatecourseList(MyDatecourse myDatecourse) {
		return myDatecourseRepository.getMyDatecourseList(myDatecourse);
	}
	
	@Override
	public void deleteMyDatecourse(MyDatecourseId myDatecourseId) {
		
		myDatecourseRepository.deleteById(myDatecourseId);
	}
	
	@Override
	public void insertMyDatecourse(MyDatecourse myDatecourse) {
		myDatecourseRepository.save(myDatecourse);
	}
	@Override
	public void deleteMyDatecourseList(List<MyDatecourse> myDatecourseList) {
		myDatecourseRepository.deleteAll(myDatecourseList);
	}
}
