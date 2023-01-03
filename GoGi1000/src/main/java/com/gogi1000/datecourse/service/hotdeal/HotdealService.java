package com.gogi1000.datecourse.service.hotdeal;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gogi1000.datecourse.entity.DatecourseImage;
import com.gogi1000.datecourse.entity.Hotdeal;

public interface HotdealService {
	Hotdeal getHotdeal(int HotdealNo);
	
	List<Hotdeal> getHotdealList(Hotdeal hotdeal);
	
	List<DatecourseImage> getHotdealImageList(int hotdealNo);
	
	Page<Hotdeal> getPageHotdealList(Hotdeal hotdeal, Pageable pageable);
	
	void insertHotdeal(Hotdeal hotdeal, List<DatecourseImage> uploadImageList);
	
	void updateHotdealUseYn(int HotdealNo);
	
	
	
}
