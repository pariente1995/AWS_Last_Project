package com.gogi1000.datecourse.service.main.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.entity.Hotdeal;
import com.gogi1000.datecourse.repository.DatecourseRepository;
import com.gogi1000.datecourse.repository.HotdealRepository;
import com.gogi1000.datecourse.service.main.MainService;

@Service
public class MainServiceImpl implements MainService{
	
	@Autowired
	private DatecourseRepository datecourseRepository;
	
	@Autowired 
	private HotdealRepository hotdealRepository;
	
	// 검색창에서 지역명, 코스명으로 검색 후 조회
	@Override
	public List<Datecourse> getSearchDatecourseList(Datecourse datecourse) {
		String searchKeyword = datecourse.getSearchKeyword();
		if(searchKeyword != null && !searchKeyword.equals("")) {
			return datecourseRepository.findByDatecourseAreaContainingOrDatecourseNmContaining(searchKeyword, searchKeyword);
		} else {
			return datecourseRepository.findAll();
		}
	}
	

	// 지도에서 지역 선택 후 조회
	@Override
	public List<CamelHashMap> getMapDatecourseList(String datecourseArea) {
		return datecourseRepository.findBySelectedDatecourseArea(datecourseArea);
	}
	
	// 메인에서 인기 랭킹 리스트 조회
	@Override
	public List<CamelHashMap> getRankDatecourseList() {
		return datecourseRepository.getRankDatecourseList();
	}
	
	// 메인에서 핫딜 리스트 조회
	@Override
	public List<CamelHashMap> getHotdealDatecourseList() {
		return hotdealRepository.getHotdealDatecourseList();
	}
	
	// 메인에서 핫딜 상세 페이지 조회
	@Override
	public Hotdeal getHotdeal(int hotdealNo) {
		return hotdealRepository.getHotdeal(hotdealNo);
	}
	
	
}
