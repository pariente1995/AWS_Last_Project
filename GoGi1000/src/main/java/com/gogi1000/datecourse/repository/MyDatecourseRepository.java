package com.gogi1000.datecourse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gogi1000.datecourse.dto.MyDatecourseDTO;
import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.entity.MyDatecourse;
import com.gogi1000.datecourse.entity.MyDatecourseId;


public interface MyDatecourseRepository extends JpaRepository<MyDatecourse, MyDatecourseId> {
	@Query(value="SELECT M.*, D.DATECOURSE_NM, D.DATECOURSE_NO, D.DATECOURSE_ADDR"
			+"      FROM T_GGC_MYDATECOURSE M"
			+"      LEFT JOIN T_GGC_DATECOURSE D"
			+"        ON M.DATECOURSE_NO = D.DATECOURSE_NO"
			+ "    WHERE M.DATECOURSE_NO = D.DATECOURSE_NO", nativeQuery=true)
	List<MyDatecourse> getMyDatecourseList(MyDatecourse myDatecourse);
}