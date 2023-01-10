package com.gogi1000.datecourse.service.my;

import java.util.List;

import com.gogi1000.datecourse.dto.MyDatecourseDTO;
import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.entity.MyDatecourse;
import com.gogi1000.datecourse.entity.MyDatecourseId;
import com.gogi1000.datecourse.entity.Review;

public interface MyDatecourseService {
	
	List<Datecourse> getMyDatecourseList(MyDatecourse myDatecourse);
	
	void deleteMyDatecourse(MyDatecourseId myDatecourseId);
	
	void insertMyDatecourse(MyDatecourse myDatecourse);
	
	void deleteMyDatecourseList(List<MyDatecourse> myDatecourseList);
}

