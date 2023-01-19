package com.gogi1000.datecourse.repository;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.CustomUserDetails;
import com.gogi1000.datecourse.entity.Datecourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

@Transactional // @Modifying이 일어난 메소드가 실행된 후 바로 트랜잭션이 일어날 수 있도록 / Repository 자체를 @Transactional로 선언
public interface DatecourseRepository extends JpaRepository<Datecourse, Integer> {
	// SELECT * FROM T_GGC_DATECOURSE
	// WHERE DATECOURSE_AREA LIKE '%searchKeyword1%'
	// OR DATECOURSE_NM LIKE '%searchKeyword2%'
	// OR DATECOURSE_DESC LIKE '%searchKeyword3%'
	
	// 로그인 후 메인에서 검색창에 '데이트 코스 주소, 데이트 코스명, 데이트 코스 설명'을 쓰고 검색 시, 관련 검색 내용 조회_인겸
	@Query(value="SELECT A.DATECOURSE_NO, A.DATECOURSE_NM, A.DATECOURSE_SUMMARY, A.DATECOURSE_CNT, B.IMAGE_NM, IFNULL(C.LIKE_CNT, 0) AS LIKE_CNT, IFNULL(D.LIKE_YN, 'N') AS LIKE_YN"
			+ "     FROM T_GGC_DATECOURSE A"
			+ "     JOIN T_GGC_IMAGE B ON A.DATECOURSE_NO = B.REFERENCE_NO"
			+ "					      AND B.IMAGE_GROUP = 'E0001'"
			+ "					      AND B.IMAGE_NO = (SELECT MIN(IMAGE_NO)"
			+ "                                           FROM T_GGC_IMAGE AA"
			+ "									         WHERE AA.IMAGE_GROUP = B.IMAGE_GROUP"
			+ "										       AND AA.REFERENCE_NO = B.REFERENCE_NO)"
			+ "     LEFT OUTER JOIN (SELECT BB.DATECOURSE_NO, COUNT(BB.DATECOURSE_NO) AS LIKE_CNT"
			+ "                        FROM T_GGC_LIKE BB"				
			+ "				          GROUP BY BB.DATECOURSE_NO) C ON A.DATECOURSE_NO = C.DATECOURSE_NO"
			+ "     LEFT OUTER JOIN (SELECT CC.DATECOURSE_NO, CC.LIKE_YN"
			+ "                        FROM T_GGC_LIKE CC"
			+ "						  WHERE USER_ID = :#{#customUser.getUsername}"					
			+ "				          GROUP BY CC.DATECOURSE_NO) D ON A.DATECOURSE_NO = D.DATECOURSE_NO"
			+ "    WHERE A.DATECOURSE_ADDR LIKE CONCAT('%', :datecourseAddr, '%')"
			+ "       OR A.DATECOURSE_NM LIKE CONCAT('%', :datecourseNm, '%')"
			+ "       OR A.DATECOURSE_DESC LIKE CONCAT('%', :datecourseDesc, '%')"
			+ "    ORDER BY A.DATECOURSE_RGST_DATE DESC",
		   countQuery="SELECT COUNT(*)"
		   		+ "      FROM ("
		   		+ "     SELECT A.DATECOURSE_NO, A.DATECOURSE_NM, A.DATECOURSE_SUMMARY, A.DATECOURSE_CNT, B.IMAGE_NM, IFNULL(C.LIKE_CNT, 0) AS LIKE_CNT, IFNULL(C.LIKE_YN, 'N') AS LIKE_YN"
		   		+ "		  FROM T_GGC_DATECOURSE A"
		   		+ "		  JOIN T_GGC_IMAGE B ON A.DATECOURSE_NO = B.REFERENCE_NO"
		   		+ "					        AND B.IMAGE_GROUP = 'E0001'"
		   		+ "							AND B.IMAGE_NO = (SELECT MIN(IMAGE_NO)"
		   		+ "			                                    FROM T_GGC_IMAGE AA"
		   		+ "											   WHERE AA.IMAGE_GROUP = B.IMAGE_GROUP"
		   		+ "											     AND AA.REFERENCE_NO = B.REFERENCE_NO)"
				+ "       LEFT OUTER JOIN (SELECT BB.DATECOURSE_NO, COUNT(BB.DATECOURSE_NO) AS LIKE_CNT"
				+ "                          FROM T_GGC_LIKE BB"				
				+ "				            GROUP BY BB.DATECOURSE_NO) C ON A.DATECOURSE_NO = C.DATECOURSE_NO"
				+ "       LEFT OUTER JOIN (SELECT CC.DATECOURSE_NO, CC.LIKE_YN"
				+ "                          FROM T_GGC_LIKE CC"
				+ "						    WHERE USER_ID = :#{#customUser.getUsername}"					
				+ "				            GROUP BY CC.DATECOURSE_NO) D ON A.DATECOURSE_NO = D.DATECOURSE_NO"
		   		+ "			    WHERE A.DATECOURSE_ADDR LIKE CONCAT('%', :datecourseAddr, '%')"
		   		+ "			       OR A.DATECOURSE_NM LIKE CONCAT('%', :datecourseNm, '%')"
		   		+ "			       OR A.DATECOURSE_DESC LIKE CONCAT('%', :datecourseDesc, '%')"
				+ "             ORDER BY A.DATECOURSE_RGST_DATE DESC"
		   		+ "    ) A",
		   nativeQuery=true)
	Page<CamelHashMap> getSearchDatecourseList( @Param("datecourseAddr") String datecourseAddr, 
												@Param("datecourseNm")String datecourseNm, 
												@Param("datecourseDesc") String datecourseDesc,
												Pageable pageable,
												@Param("customUser") CustomUserDetails customUser);
	
	// 로그인 전 메인에서 검색창에 '데이트 코스 주소, 데이트 코스명, 데이트 코스 설명'을 쓰고 검색 시, 관련 검색 내용 조회_인겸
	@Query(value="SELECT A.DATECOURSE_NO, A.DATECOURSE_NM, A.DATECOURSE_SUMMARY, A.DATECOURSE_CNT, B.IMAGE_NM, IFNULL(C.LIKE_CNT, 0) AS LIKE_CNT, 'N' AS LIKE_YN"
			+ "     FROM T_GGC_DATECOURSE A"
			+ "     JOIN T_GGC_IMAGE B ON A.DATECOURSE_NO = B.REFERENCE_NO"
			+ "					      AND B.IMAGE_GROUP = 'E0001'"
			+ "					      AND B.IMAGE_NO = (SELECT MIN(IMAGE_NO)"
			+ "                                           FROM T_GGC_IMAGE AA"
			+ "									         WHERE AA.IMAGE_GROUP = B.IMAGE_GROUP"
			+ "										       AND AA.REFERENCE_NO = B.REFERENCE_NO)"
			+ "     LEFT OUTER JOIN (SELECT BB.DATECOURSE_NO, COUNT(BB.DATECOURSE_NO) AS LIKE_CNT"
			+ "                        FROM T_GGC_LIKE BB"					
			+ "				          GROUP BY BB.DATECOURSE_NO) C ON A.DATECOURSE_NO = C.DATECOURSE_NO"
			+ "    WHERE A.DATECOURSE_ADDR LIKE CONCAT('%', :datecourseAddr, '%')"
			+ "       OR A.DATECOURSE_NM LIKE CONCAT('%', :datecourseNm, '%')"
			+ "       OR A.DATECOURSE_DESC LIKE CONCAT('%', :datecourseDesc, '%')"
			+ "    ORDER BY A.DATECOURSE_RGST_DATE DESC",
		   countQuery="SELECT COUNT(*)"
		   		+ "      FROM ("
		   		+ "     SELECT A.DATECOURSE_NO, A.DATECOURSE_NM, A.DATECOURSE_SUMMARY, A.DATECOURSE_CNT, B.IMAGE_NM, IFNULL(C.LIKE_CNT, 0) AS LIKE_CNT, 'N' AS LIKE_YN"
		   		+ "		  FROM T_GGC_DATECOURSE A"
		   		+ "		  JOIN T_GGC_IMAGE B ON A.DATECOURSE_NO = B.REFERENCE_NO"
		   		+ "					        AND B.IMAGE_GROUP = 'E0001'"
		   		+ "							AND B.IMAGE_NO = (SELECT MIN(IMAGE_NO)"
		   		+ "			                                    FROM T_GGC_IMAGE AA"
		   		+ "											   WHERE AA.IMAGE_GROUP = B.IMAGE_GROUP"
		   		+ "											     AND AA.REFERENCE_NO = B.REFERENCE_NO)"
		   		+ "	      LEFT OUTER JOIN (SELECT BB.DATECOURSE_NO, COUNT(BB.DATECOURSE_NO) AS LIKE_CNT"
		   		+ "			                 FROM T_GGC_LIKE BB"		
		   		+ "							GROUP BY BB.DATECOURSE_NO) C ON A.DATECOURSE_NO = C.DATECOURSE_NO"
		   		+ "			    WHERE A.DATECOURSE_ADDR LIKE CONCAT('%', :datecourseAddr, '%')"
		   		+ "			       OR A.DATECOURSE_NM LIKE CONCAT('%', :datecourseNm, '%')"
		   		+ "			       OR A.DATECOURSE_DESC LIKE CONCAT('%', :datecourseDesc, '%')"
				+ "             ORDER BY A.DATECOURSE_RGST_DATE DESC"
		   		+ "    ) A",
		   nativeQuery=true)
	Page<CamelHashMap> getSearchDatecourseList( @Param("datecourseAddr") String datecourseAddr, 
												@Param("datecourseNm") String datecourseNm, 
												@Param("datecourseDesc") String datecourseDesc,
												Pageable pageable);
	
	
	// \r: 커서를 가장 왼쪽으로 이동
	// \n: 커서를 한칸 아래로 이동
	// serviceImpl에서 nativeQuery로 보내면 Query어노테이션에 nativeQuery처리 해줘야됨.
	
	// 로그인 후 해당 지역 클릭 시 '지역' 관련 리스트들 조회_인겸
	// 데이터의 변경이 일어나는 @Query을 사용할 떄는 @Modifying 어노테이션을 사용해야한다.
	@Query(value = "SELECT A.DATECOURSE_NO, A.DATECOURSE_NM, A.DATECOURSE_SUMMARY, A.DATECOURSE_CNT, IFNULL(B.LIKE_CNT, 0) AS LIKE_CNT, IFNULL(C.LIKE_YN, 'N') AS LIKE_YN, D.IMAGE_NM"
		+ "			  FROM T_GGC_DATECOURSE A"
		+ "			  LEFT OUTER JOIN ("
		+ "								SELECT D.DATECOURSE_NO"
		+ "									 , COUNT(D.DATECOURSE_NO) AS LIKE_CNT"
		+ "								  FROM T_GGC_LIKE D"
		+ "								 GROUP BY D.DATECOURSE_NO"
		+ "								) B ON A.DATECOURSE_NO = B.DATECOURSE_NO"
		+ "			  LEFT OUTER JOIN ("
		+ "								SELECT E.DATECOURSE_NO"
		+ "									 , E.LIKE_YN"
		+ "								  FROM T_GGC_LIKE E"
		+ "							     WHERE E.USER_ID = :#{#customUser.getUsername}"
		+ "							   ) C ON A.DATECOURSE_NO = C.DATECOURSE_NO"
		+ "			  JOIN T_GGC_IMAGE D ON A.DATECOURSE_NO = D.REFERENCE_NO"
		+ "			                    AND D.IMAGE_GROUP = 'E0001'"
		+ "                             AND D.IMAGE_NO = ("
		+ "												 	SELECT MIN(IMAGE_NO)"
		+ "                                                   FROM T_GGC_IMAGE AA"
		+ "													 WHERE AA.IMAGE_GROUP = D.IMAGE_GROUP"
		+ "                                                    AND AA.REFERENCE_NO = D.REFERENCE_NO"
		+ "                                              )"
		+ "			 WHERE A.DATECOURSE_AREA = :datecourseArea"
		+ "          ORDER BY A.DATECOURSE_RGST_DATE DESC",
		countQuery="SELECT COUNT(*)"
		+ "			  FROM ("
		+ "					SELECT A.DATECOURSE_NO, A.DATECOURSE_NM, A.DATECOURSE_SUMMARY, A.DATECOURSE_CNT, IFNULL(B.LIKE_CNT, 0) AS LIKE_CNT, IFNULL(C.LIKE_YN, 'N') AS LIKE_YN, D.IMAGE_NM"
		+ "					  FROM T_GGC_DATECOURSE A"
		+ "					  LEFT OUTER JOIN ("
		+ "										SELECT D.DATECOURSE_NO"
		+ "											 , COUNT(D.USER_ID) AS LIKE_CNT"
		+ "										  FROM T_GGC_LIKE D"
		+ "									  	 GROUP BY D.DATECOURSE_NO"
		+ "									    ) B ON A.DATECOURSE_NO = B.DATECOURSE_NO"
		+ "					  LEFT OUTER JOIN ("
		+ "										SELECT E.DATECOURSE_NO"
		+ "											 , E.LIKE_YN"
		+ "										  FROM T_GGC_LIKE E"
		+ "							             WHERE E.USER_ID = :#{#customUser.getUsername}"
		+ "									    ) C ON A.DATECOURSE_NO = C.DATECOURSE_NO"
		+ "					  JOIN T_GGC_IMAGE D ON A.DATECOURSE_NO = D.REFERENCE_NO"
		+ "					                    AND D.IMAGE_GROUP = 'E0001'"
		+ "		                                AND D.IMAGE_NO = ("
		+ "														 	SELECT MIN(IMAGE_NO)"
		+ "		                                                      FROM T_GGC_IMAGE AA"
		+ "														     WHERE AA.IMAGE_GROUP = D.IMAGE_GROUP"
		+ "		                                                   	   AND AA.REFERENCE_NO = D.REFERENCE_NO"
		+ "		                                                  )"
		+ "					 WHERE A.DATECOURSE_AREA = :datecourseArea"
		+ "                  ORDER BY A.DATECOURSE_RGST_DATE DESC"
		+ "				) E",
	    //Page<T>로 리턴할 때 사용 countQuery = "",
	    nativeQuery = true)
	Page<CamelHashMap> getMapDatecourseList(@Param("datecourseArea") String datecourseArea, Pageable pageable,
											@Param("customUser") CustomUserDetails customUser);
	
	
	// 로그인 전 해당 지역 클릭 시 '지역' 관련 리스트들 조회_인겸
	// 데이터의 변경이 일어나는 @Query을 사용할 떄는 @Modifying 어노테이션을 사용해야한다.
	@Query(value = "SELECT A.DATECOURSE_NO, A.DATECOURSE_NM, A.DATECOURSE_SUMMARY, A.DATECOURSE_CNT, IFNULL(B.LIKE_CNT, 0) AS LIKE_CNT, 'N' AS LIKE_YN, C.IMAGE_NM"
		+ "			  FROM T_GGC_DATECOURSE A"
		+ "			  LEFT OUTER JOIN ("
		+ "								SELECT D.DATECOURSE_NO"
		+ "									 , COUNT(D.DATECOURSE_NO) AS LIKE_CNT"
		+ "								  FROM T_GGC_LIKE D"
		+ "								 GROUP BY D.DATECOURSE_NO"
		+ "								) B ON A.DATECOURSE_NO = B.DATECOURSE_NO"
		+ "			  JOIN T_GGC_IMAGE C ON A.DATECOURSE_NO = C.REFERENCE_NO"
		+ "			                    AND C.IMAGE_GROUP = 'E0001'"
		+ "                             AND C.IMAGE_NO = ("
		+ "												 	SELECT MIN(IMAGE_NO)"
		+ "                                                   FROM T_GGC_IMAGE AA"
		+ "													 WHERE AA.IMAGE_GROUP = C.IMAGE_GROUP"
		+ "                                                    AND AA.REFERENCE_NO = C.REFERENCE_NO"
		+ "                                              )"
		+ "			 WHERE A.DATECOURSE_AREA = :datecourseArea"
		+ "		     ORDER BY A.DATECOURSE_RGST_DATE DESC",
		countQuery="SELECT COUNT(*)"
		+ "			  FROM ("
		+ "					SELECT A.DATECOURSE_NO, A.DATECOURSE_NM, A.DATECOURSE_SUMMARY, A.DATECOURSE_CNT, IFNULL(B.LIKE_CNT, 0) AS LIKE_CNT, 'N' AS LIKE_YN, C.IMAGE_NM"
		+ "					  FROM T_GGC_DATECOURSE A"
		+ "					  LEFT OUTER JOIN ("
		+ "										SELECT D.DATECOURSE_NO"
		+ "											 , COUNT(D.DATECOURSE_NO) AS LIKE_CNT"
		+ "										  FROM T_GGC_LIKE D"
		+ "									  	 GROUP BY D.DATECOURSE_NO"
		+ "									    ) B ON A.DATECOURSE_NO = B.DATECOURSE_NO"
		+ "					  JOIN T_GGC_IMAGE C ON A.DATECOURSE_NO = C.REFERENCE_NO"
		+ "					                    AND C.IMAGE_GROUP = 'E0001'"
		+ "		                                AND C.IMAGE_NO = ("
		+ "														 	SELECT MIN(IMAGE_NO)"
		+ "		                                                      FROM T_GGC_IMAGE AA"
		+ "														     WHERE AA.IMAGE_GROUP = C.IMAGE_GROUP"
		+ "		                                                   	   AND AA.REFERENCE_NO = C.REFERENCE_NO"
		+ "		                                                  )"
		+ "					 WHERE A.DATECOURSE_AREA = :datecourseArea"
		+ "                  ORDER BY A.DATECOURSE_RGST_DATE DESC"
		+ "				) E",
	    //Page<T>로 리턴할 때 사용 countQuery = "",
	    nativeQuery = true)
	Page<CamelHashMap> getMapDatecourseList(@Param("datecourseArea") String datecourseArea, Pageable pageable);
	
	// A.*하면 전체 컬럼 가져와버림. 필요한 것만 가져오는 것이 좋음
	// B 테이블의 IMAGE_NM 컬럼으로 이미지를 가지고 온다.
	// 좋아요 많은 순으로 인기 랭킹 TOP 10 조회
	// CamelHashMap 사용 이유: 다른 2개 이상의 테이블을 join할 때, join된 컬럼들을 모두 가지고 있는 entity가 존재하지 않으므로, map으로 받아준다.
	
	// 메인에서 인기 리스트 조회, 이미지 Min으로 1개만 받아오기_인겸
	@Modifying
	@Query(value = "SELECT A.*, "
			+"			   C.IMAGE_NM"
			+"		  FROM T_GGC_DATECOURSE A"
			+"	LEFT  JOIN ( SELECT AA.DATECOURSE_NO, COUNT(AA.DATECOURSE_NO) AS 'DATECOURSE_CNT'"
			+"				   FROM	T_GGC_LIKE AA"
			+"				  GROUP BY AA.DATECOURSE_NO ) B ON A.DATECOURSE_NO = B.DATECOURSE_NO"
			+"		  JOIN T_GGC_IMAGE C ON A.DATECOURSE_NO = C.REFERENCE_NO"
			+"		 WHERE C.IMAGE_GROUP = 'E0001'"
			+"		   AND C.IMAGE_NO = ("
			+"								SELECT MIN(D.IMAGE_NO)"
			+"								  FROM T_GGC_IMAGE D"
			+"								 WHERE D.IMAGE_GROUP = C.IMAGE_GROUP"
			+"								   AND D.REFERENCE_NO = C.REFERENCE_NO"
			+"								)"
			+"		 ORDER BY B.DATECOURSE_CNT DESC"	
			+"		 LIMIT 10",
			nativeQuery = true)
	List<CamelHashMap> getRankDatecourseList();

	// 데이트 코스 리스트 화면(관리자)에서 datecourseArea로 검색_세혁
	Page<Datecourse> findByDatecourseArea(String datecourseArea, Pageable pageable);

	// 데이트 코스 리스트 화면(관리자)에서 datecourseCategory로 검색_세혁
	Page<Datecourse> findByDatecourseCategory(String datecourseCategory, Pageable pageable);

	// 데이트 코스 리스트 화면(관리자)에서 datecourseNm으로 검색_세혁
	Page<Datecourse> findByDatecourseNmContaining(String searchKeyword, Pageable pageable);

	// 데이트 코스 리스트 화면(관리자)에서 datecourseArea, datecourseCategory로 검색_세혁
	Page<Datecourse> findByDatecourseAreaAndDatecourseCategory(String datecourseArea, String datecourseCategory, Pageable pageable);

	// 데이트 코스 리스트 화면(관리자)에서 datecourseArea, datecourseNm으로 검색_세혁
	Page<Datecourse> findByDatecourseAreaAndDatecourseNmContaining(String datecourseArea, String searchKeyword, Pageable pageable);

	// 데이트 코스 리스트 화면(관리자)에서 datecourseCategory, datecourseNm으로 검색_세혁
	Page<Datecourse> findByDatecourseCategoryAndDatecourseNmContaining(String datecourseCategory, String searchKeyword, Pageable pageable);

	// 데이트 코스 리스트 화면(관리자)에서 datecourseArea, datecourseCategory, datecourseNm으로 검색_세혁
	Page<Datecourse> findByDatecourseAreaAndDatecourseCategoryAndDatecourseNmContaining(String datecourseArea, String datecourseCategory, String searchKeyword, Pageable pageable);

	// 데이트 코스 리스트 화면(관리자)에서 삭제 시, 데이트 코스 사용여부를 ('Y' -> 'N')으로 업데이트_세혁
	@Modifying
	@Query(value="UPDATE T_GGC_DATECOURSE "
			    +"   SET DATECOURSE_USE_YN = 'N' "
			    +" WHERE DATECOURSE_NO = :datecourseNo", nativeQuery = true)
	void updateDatecourseList(@Param("datecourseNo") int datecourseNo) ;
	
	// 메인에서 인기 상세 페이지 조회 시 '조회수' 증가_인겸
	@Modifying	// 데이터의 변경이 일어나는 @Query을 사용할 떄는 @Modifying 어노테이션을 사용해야한다.
	@Query(value="UPDATE T_GGC_DATECOURSE"
			+ "		 SET DATECOURSE_CNT = DATECOURSE_CNT + 1"
			+ "	   WHERE DATECOURSE_NO = :datecourseNo", 
			nativeQuery = true)
	void updateCateDatecourseCnt(@Param("datecourseNo") int datecourseNo);
	
	// 메인에서 인기 '상세' 페이지 조회_인겸
	@Query(value="SELECT A.*"
			+ "		FROM T_GGC_DATECOURSE A"
			+ "	   WHERE A.DATECOURSE_NO = :datecourseNo",
			nativeQuery = true)
	Datecourse getCateDatecourse(@Param("datecourseNo") int datecourseNo);
	
	// 메인에서 인기 상세 페이지 조회 시, 데이트 코스 '메뉴' 리스트 조회_인겸
	@Query(value="SELECT A.*"
			+ "		FROM T_GGC_DATECOURSE_MENU A"
			+ "	   WHERE A.DATECOURSE_NO = :datecourseNo",
			nativeQuery = true)
	List<CamelHashMap> getCateDatecourseMenu(@Param("datecourseNo") int datecourseNo);
	
	
}
