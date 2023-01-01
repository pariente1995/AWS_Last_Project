package com.gogi1000.datecourse.service.main;

import java.util.List;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Datecourse;

public interface MainService {
	List<Datecourse> getCateDatecourseList(Datecourse datecourse);

	List<CamelHashMap> getCateDatecourseList(String datecourseArea);
}
