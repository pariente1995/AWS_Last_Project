package com.gogi1000.datecourse.service.main.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.repository.MainRepository;
import com.gogi1000.datecourse.service.main.MainService;

@Service
public class MainServiceImpl implements MainService{
	@Autowired
	private MainRepository mainRepository;
	
	// Service를 받아서 다시 쓰겠다.
	@Override
	public List<Datecourse> getCateDatecourseList(Datecourse datecourse) {
		String searchKeyword = datecourse.getSearchKeyword();
		if(searchKeyword != null && !searchKeyword.equals("")) {
			return mainRepository.findByDatecourseNmContainingOrDatecourseAreaContaining(searchKeyword, searchKeyword);
		} else {
			return mainRepository.findAll();
		}
	}
	
	@Override
	public List<CamelHashMap> getCateDatecourseList(String datecourseArea) {
		return mainRepository.findBySelectedDatecourseArea(datecourseArea);
	}
}
