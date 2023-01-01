package com.gogi1000.datecourse.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Datecourse;

//JpaRepository(부모)한테 상속받음
@Transactional	// @Modifying이 일어난 메소드가 실행된 후 바로 트랜잭션이 일어날 수 있도록
				// Repository 자체를 @Transactional로 선언
				// JpaRepository<해당클래스, 해당 클래스 key타입>
public interface MainRepository extends JpaRepository<Datecourse, Integer>{
	/*
	 JPA 규칙 메소드
	 find, read, query, count, get
	 엔티티의 변수명으로 조건을 달아줘야함.
	 boardNo으로 List<BoardFileTest> 검색하려면
	 findbyBoardBoardNo(int boardNo); 
	  => SELECT * FROM T_BOARD_FILE_TEST WHERE BOARD_NO = :boardNo;
	 findbyBoard(Board boardTest); , 위에 거 동일하게 됌.
	 AND, OR 두 컬럼 사이에 And Or를 붙여준다.
	 boardTitle, boardContent로 검색할 때
	 findbyBoardTitleAnd(Or)BoardContent(Board boardTest)
	  => SELECT * FROM T_BOARD_TEST 
			WHERE BOARD_TITLE = :boardTitle AND BOARD_CONTENT = :boardContent
	 Containing == like '%keyword%'
	*/
	

	// boardTitle 검색
	// Containing == like '%keyword%'
	// SELECT * FROM T_BOARD_TEST WHERE BOARD_TITLE LIKE '%searchKeyword%'
	List<Datecourse> findByDatecourseNmContaining(String searchKeyword);
	
	// boardContent 검색
	// SELECT * FROM T_BOARD_TEST WHERE BOARD_CONTENT LIKE '%searchKeyword%'
	List<Datecourse> findByDatecourseAreaContaining(String searchKeyword);
	
	
	// SELECT * FROM T_BOARD_TEST 
	// WHERE BOARD_TITLE LIKE '%searchKeyword1%'
	// OR BOARD_CONTENT LIKE '%searchKeyword2%'
	// OR BOARD_WRITER LIKE '%searchKeyword3%'
	List<Datecourse> findByDatecourseNmContainingOrDatecourseAreaContaining(String searchKeyword, String searchKeyword1);
	
	
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
			+ "										SELECT D.DATACOURSE_NO\"\r\n"
			+ "											FROM T_GGC_DATECOURSE D\"\r\n"
			+ "											WHERE D.DATECOURSE_AREA LIKE CONCAT('%', :datecourseArea, '%')\"\r\n"
			+ "								   )\"",
		   //Page<T>로 리턴할 때 사용 countQuery = "",
		   nativeQuery = true)
	List<CamelHashMap> findBySelectedDatecourseArea(@Param("datecourseArea") String datecourseArea);
}
