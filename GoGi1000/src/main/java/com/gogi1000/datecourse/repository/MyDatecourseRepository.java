package com.gogi1000.datecourse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.CustomUserDetails;
import com.gogi1000.datecourse.entity.MyDatecourse;
import com.gogi1000.datecourse.entity.MyDatecourseId;


public interface MyDatecourseRepository extends JpaRepository<MyDatecourse, MyDatecourseId> {
	@Query(value="SELECT M.*, D.DATECOURSE_NM, D.DATECOURSE_NO, D.DATECOURSE_ADDR"
			+"      FROM T_GGC_MYDATECOURSE M"
			+"      LEFT JOIN T_GGC_DATECOURSE D"
			+"        ON M.DATECOURSE_NO = D.DATECOURSE_NO"
			+"    WHERE M.DATECOURSE_NO = D.DATECOURSE_NO"
			+"      AND M.USER_ID = :#{#MyDatecourse.userId}"
			+ "   ORDER BY M.MY_DATECOURSE_RGST_DATE ASC"
			+ "	  LIMIT 10", nativeQuery=true)
	List<CamelHashMap> getMyDatecourseList(@Param("MyDatecourse") MyDatecourse mydatecourse);
	
	// MY 데이트 코스 삭제_김도원
	@Modifying
	@Query(value="DELETE FROM T_GGC_MYDATECOURSE"
			+ "    WHERE DATECOURSE_NO = :datecourseNo"
			, nativeQuery = true)
	void deleteByDatecourseNo(@Param("datecourseNo") int datecourseNo);
	
	@Query(value="SELECT DATECOURSE_NO, USER_ID,'N' AS MY_DATECOURSE_YN, MY_DATECOURSE_RGST_DATE"
			+"      FROM T_GGC_MYDATECOURSE"
			+"     WHERE DATECOURSE_NO = :#{#myDatecourse.datecourseNo}",nativeQuery=true)
	MyDatecourse getMyDatecourse(@Param("myDatecourse") MyDatecourse myDatecourse);
	
	@Query(value="SELECT DATECOURSE_NO, USER_ID, IFNULL(MY_DATECOURSE_YN, 'N') AS MY_DATECOURSE_YN, MY_DATECOURSE_RGST_DATE"
			+"      FROM T_GGC_MYDATECOURSE"
			+"     WHERE DATECOURSE_NO = :#{#myDatecourse.datecourseNo}"
			+ "		 AND USER_ID = :#{#customUser.getUsername}",nativeQuery=true)
	MyDatecourse getMyDatecourse(@Param("myDatecourse") MyDatecourse myDatecourse,@Param("customUser") CustomUserDetails CustomUser);
	
}
