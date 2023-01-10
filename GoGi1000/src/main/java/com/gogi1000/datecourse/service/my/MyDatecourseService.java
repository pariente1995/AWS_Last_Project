package com.gogi1000.datecourse.service.my;

import java.util.List;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.MyDatecourse;
import com.gogi1000.datecourse.entity.MyDatecourseId;

public interface MyDatecourseService {
	
	List<CamelHashMap> getMyDatecourseList(MyDatecourse myDatecourse);
	
	void deleteMyDatecourse(MyDatecourseId myDatecourseId);
	
	void insertMyDatecourse(MyDatecourse myDatecourse);
	
	void deleteMyDatecourseList(List<MyDatecourse> myDatecourseList);
}

