package com.gogi1000.datecourse.repository;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.MyDatecourse;
import com.gogi1000.datecourse.entity.MyDatecourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MyDatecourseRepository extends JpaRepository<MyDatecourse, MyDatecourseId> {
	@Query(value="SELECT M.*, D.DATECOURSE_NM, D.DATECOURSE_NO, D.DATECOURSE_ADDR"
			+"      FROM T_GGC_MYDATECOURSE M"
			+"      LEFT JOIN T_GGC_DATECOURSE D"
			+"        ON M.DATECOURSE_NO = D.DATECOURSE_NO"
			+"    WHERE M.DATECOURSE_NO = D.DATECOURSE_NO"
			+"      AND M.USER_ID = :#{#MyDatecourse.userId}"
			+ "   ORDER BY M.MY_DATECOURSE_RGST_DATE ASC", nativeQuery=true)
	List<CamelHashMap> getMyDatecourseList(@Param("MyDatecourse") MyDatecourse mydatecourse);

	// MY 데이트 코스 삭제_세혁
	@Modifying
	@Query(value="DELETE FROM T_GGC_MYDATECOURSE"
			+ "    WHERE DATECOURSE_NO = :datecourseNo"
			, nativeQuery = true)
	void deleteByDatecourseNo(@Param("datecourseNo") int datecourseNo);
}
