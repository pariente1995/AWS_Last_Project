package com.gogi1000.datecourse.repository;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Datecourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import javax.transaction.Transactional;

@Transactional // @Modifying이 일어난 메소드가 실행된 후 바로 트랜잭션이 일어날 수 있도록 / Repository 자체를 @Transactional로 선언
public interface DatecourseRepository extends JpaRepository<Datecourse, Integer> {
	// datecourseArea 검색
	// SELECT * FROM T_GGC_DATECOURSE WHERE DATECOURSE_AREA LIKE '%searchKeyword%'
//	List<Datecourse> findByDatecourseAreaContaining(String searchKeyword);

	// datecourseNm 검색
	// Containing == like '%keyword%'
	// SELECT * FROM T_GGC_DATECOURSE WHERE DATECOURSE_NM LIKE '%searchKeyword%'
//	List<Datecourse> findByDatecourseNmContaining(String searchKeyword);
	
	
	// SELECT * FROM T_GGC_DATECOURSE
	// WHERE DATECOURSE_AREA LIKE '%searchKeyword1%'
	// OR DATECOURSE_NM LIKE '%searchKeyword2%'
	List<Datecourse> findByDatecourseAreaContainingOrDatecourseNmContaining(String searchKeyword, String searchKeyword1);
	
	
	// \r: 커서를 가장 왼쪽으로 이동
	// \n: 커서를 한칸 아래로 이동
	// serviceImpl에서 nativeQuery로 보내면 Query어노테이션에 nativeQuery처리 해줘야됨.
	@Modifying	// 데이터의 변경이 일어나는 @Query을 사용할 떄는 @Modifying 어노테이션을 사용해야한다.
	@Query(value = "SELECT A.*"
			+ "			 , IFNULL(B.LIKE_CNT, 0) AS LIKE_CNT"
			+ "			FROM T_GGC_DATECOURSE A"
			+ "			LEFT OUTER JOIN ("
			+ "								SELECT C.DATECOURSE_NO"
			+ "									 , COUNT(C.DATECOURSE_NO) AS LIKE_CNT"
			+ "									FROM T_GGC_LIKE C"
			+ "									GROUP BY C.DATECOURSE_NO"
			+ "							) B"
			+ "			ON A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ "			AND A.DATECOURSE_NO IN ("
			+ "										SELECT D.DATECOURSE_NO"
			+ "											FROM T_GGC_DATECOURSE D"
			+ "											WHERE D.DATECOURSE_AREA LIKE CONCAT('%', :datecourseArea, '%')"
			+ "								   )",
		   //Page<T>로 리턴할 때 사용 countQuery = "",
		   nativeQuery = true)
	List<CamelHashMap> findBySelectedDatecourseArea(@Param("datecourseArea") String datecourseArea);
	

}
