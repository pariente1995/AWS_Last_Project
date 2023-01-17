package com.gogi1000.datecourse.service.main;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.entity.DatecourseImage;
import com.gogi1000.datecourse.entity.Hotdeal;

public interface MainService {
	// 검색창에서 지역명, 코스명, 내용으로 검색 후 조회_인겸
	Page<CamelHashMap> getSearchMapDatecourseList(Datecourse datecourse,Pageable pageable);
	
	// 메인에서 인기 리스트 조회_인겸
	List<CamelHashMap> getRankDatecourseList();
	
	// 메인에 핫딜 리스트 조회_인겸
	List<CamelHashMap> getHotdealDatecourseList();
	
	// 메인에서 인기 상세 데이트 코스 조회 시, 조회수 증가_인겸
	void updateCateDatecourseCnt(int datecourseNo);
	
	// 메인에서 인기 상세 데이트 코스 조회_인겸
	Datecourse getCateDatecourse(int datecourseNo);
	
	// 메인에서 인기 상세 데이트 코스 조회 시, 데이트코스 메뉴 이름, 가격 조회_인겸
	List<CamelHashMap> getCateDatecourseMenu(int datecourseNo);
	
	// 메인에서 인기 상세 데이트 코스 조회 시, 데이트 코스 영업 시간 조회_인겸
	List<CamelHashMap> getCateDatecourseHours(int datecourseNo);
	
	// 인기 상세 페이지 이미지 조회_인겸
	List<DatecourseImage> getDatecourseImageList(int datecourseNo);
	
	// 메인에서 핫딜 상세 페이지 조회
	Hotdeal getHotdeal(int hotdealNo);
	
	// 핫딜 상세 페이지 이미지 조회
	List<DatecourseImage> getDatecourseHotdealImageList(int hotdealNo);
	
}