package com.gogi1000.datecourse.service.main;

import java.util.List;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.entity.Hotdeal;

public interface MainService {
	// 검색창에서 지역명, 코스명으로 검색 후 조회
	List<Datecourse> getSearchDatecourseList(Datecourse datecourse);

	// 지도에서 선택 후 조회
	List<CamelHashMap> getMapDatecourseList(String datecourseArea);
	
	// 탑텐 선택 후 조회
	List<CamelHashMap> getRankDatecourseList();
	
	// 메인에 핫딜 리스트 조회
	List<CamelHashMap> getHotdealDatecourseList();
	
	// 메인에서 핫딜 상세 페이지 조회
	Hotdeal getHotdeal(int hotdealNo);
	
}
