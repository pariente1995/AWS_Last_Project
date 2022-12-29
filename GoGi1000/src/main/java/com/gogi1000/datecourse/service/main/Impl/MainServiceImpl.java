package com.gogi1000.datecourse.service.main.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.repository.MainRepository;
import com.gogi1000.datecourse.service.main.MainService;

@Service
public class MainServiceImpl implements MainService{
	@Autowired
	private MainRepository mainRepository;
	
	public List<Datecourse> getDatecourseList(Datecourse datecourse) {
		return mainRepository.findAll();
	}
}
