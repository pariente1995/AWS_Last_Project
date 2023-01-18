package com.gogi1000.datecourse.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Hotdeal;

@Transactional
public interface HotdealRepository extends JpaRepository<Hotdeal, Integer> {

	//핫딜 리스트 삭제버튼 클릭시 UseYn('Y' => 'N')으로 변경_도원
	@Modifying
	@Query(value="UPDATE T_GGC_HOTDEAL SET HOTDEAL_USE_YN = 'N' WHERE HOTDEAL_NO = :hotdealNo", nativeQuery=true)
	void updateHotdealUseYn(@Param("hotdealNo") int hotdealNo);
	
	//조회 조건 ALL일 때 검색어 검색_도원
	Page<Hotdeal> findByHotdealNmContaining(String searchKeyword, Pageable pageable);
	Page<Hotdeal> findByHotdealDescContaining(String searchKeyword, Pageable pageable);
	Page<Hotdeal> findByHotdealNmContainingOrHotdealDescContaining(String searchKeyword1, String searchKeyword2, Pageable pageable);
	  /* 
	   	메인에서 핫딜리스트 조회
	   	JOIN T_GGC_IMAGE 하여, B.IMAGE_NM으로 이미지 받아옴
	   	HOTDEAL_NO로 오름차순 정렬
	 */
	@Modifying
	@Query(value = "SELECT A.*, "
			+ "			   B.IMAGE_NM"
			+ "		  FROM T_GGC_HOTDEAL A"
			+ "		  JOIN T_GGC_IMAGE B"
			+ "		    ON A.HOTDEAL_NO = B.REFERENCE_NO"
			+ "		 WHERE B.IMAGE_GROUP = 'E0002'"
			+ "		   AND A.HOTDEAL_USE_YN = 'Y'"
			+ "		 LIMIT 10",
			nativeQuery=true)
	List<CamelHashMap> getHotdealDatecourseList();
	
	
	// 메인에서 핫딜 상세 페이지 조회
	@Modifying
	@Query(value = "SELECT *"
			+ "		  FROM T_GGC_HOTDEAL"
			+ "		 WHERE HOTDEAL_NO = :hotdealNo", nativeQuery=true)
	Hotdeal getHotdeal(@Param("hotdealNo") int hotdealNo);

	
	@Query(value="SELECT * "
			+ "	    FROM T_GGC_HOTDEAL"
			+ "   WHERE HOTDEAL_USE_YN = 'Y'",
			countQuery=" SELECT COUNT(*)"
					+ "    FROM ("
					+ "          SELECT * FROM T_GGC_HOTDEAL"
					+ "           WHERE HOTDEAL_USE_YN = 'Y') A", nativeQuery=true) 
	Page<Hotdeal> getYList(Pageable pageable);
	
	
	@Query(value="SELECT * "
			+ "	   FROM T_GGC_HOTDEAL"
			+ "   WHERE 1=0"
			+ "      OR HOTDEAL_NM LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
			+ "		 OR HOTDEAL_DESC LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
			+ "      OR HOTDEAL_SUMMARY LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
			+ "     AND HOTDEAL_USE_YN = 'Y'",
			countQuery=" SELECT COUNT(*) "
					+ "    FROM ("
					+ "			SELECT * "
					+ "           FROM T_GGC_HOTDEAL"
					+ "          WHERE 1=0"
					+ "             OR HOTDEAL_NM LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
					+ "		 	    OR HOTDEAL_DESC LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
					+ "      		OR HOTDEAL_SUMMARY LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
					+ "			   AND HOTDEAL_USE_YN = 'Y') A", nativeQuery=true)
	Page<Hotdeal> getYListAll(@Param("hotdeal") Hotdeal hotdeal, Pageable pageable);
	
	
	@Query (value="SELECT *"
			+ "		 FROM T_GGC_HOTDEAL"
			+ "    WHERE HOTDEAL_NM LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
			+ "      AND HOTDEAL_USE_YN = 'Y'",
			countQuery="  SELECT COUNT(*)"
					+ "     FROM ("
					+ "          SELECT *"
					+ "            FROM T_GGC_HOTDEAL"
					+ "           WHERE HOTDEAL_NM LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
					+ "             AND HOTDEAL_USE_YN = 'Y') A", nativeQuery=true)
	Page<Hotdeal> getYListName(@Param("hotdeal") Hotdeal hotdeal, Pageable pageable);
	
	
	@Query (value="SELECT *"
			+ "      FROM T_GGC_HOTDEAL"
			+ "     WHERE HOTDEAL_DESC LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
			+ "         OR HOTDEAL_SUMMARY LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
			+ "       AND HOTDEAL_USE_YN = 'Y'",
			countQuery="   SELECT COUNT(*)"
					+ "      FROM ("
					+ "            SELECT *"
					+ "              FROM T_GGC_HOTDEAL"
					+ "             WHERE HOTDEAL_DESC LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
					+ "                OR HOTDEAL_SUMMARY LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
					+ "               AND HOTDEAL_USE_YN = 'Y') A", nativeQuery=true)
	Page<Hotdeal> getYListContent(@Param("hotdeal") Hotdeal hotdeal, Pageable pageable);
	
	
	@Query(value="SELECT * "
			+ "		FROM T_GGC_HOTDEAL"
			+ "   WHERE HOTDEAL_USE_YN = 'N'",
			countQuery=" SELECT COUNT(*)"
					+ "    FROM ("
					+ "          SELECT * FROM T_GGC_HOTDEAL"
					+ "           WHERE HOTDEAL_USE_YN = 'N') A",nativeQuery=true)
	Page<Hotdeal> getNList(Pageable pageable);
	
	
	@Query(value=" SELECT * "
			+ "		 FROM T_GGC_HOTDEAL"
			+ "     WHERE 1=0"
			+ "		   OR HOTDEAL_NM LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
			+ "        OR HOTDEAL_DESC LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
			+ "        OR HOTDEAL_SUMMARY LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
			+ "       AND HOTDEAL_USE_YN = 'N'",
			countQuery=" SELECT COUNT(*) "
					+ "    FROM ("
					+ "		     SELECT * "
					+ "            FROM T_GGC_HOTDEAL"
					+ "           WHERE 1=0"
					+ "              OR HOTDEAL_NM LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%') "
					+ "		 	     OR HOTDEAL_DESC LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
					+ "      	     OR HOTDEAL_SUMMARY LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
					+ "				AND HOTDEAL_USE_YN = 'N') A", nativeQuery=true)
	Page<Hotdeal> getNListAll(@Param("hotdeal") Hotdeal hotdeal, Pageable pageable);
	
	
	@Query (value="SELECT *"
			+ "		 FROM T_GGC_HOTDEAL"
			+ "     WHERE HOTDEAL_NM LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
			+ "       AND HOTDEAL_USE_YN = 'N'",
			countQuery="  SELECT COUNT(*)"
					+ "     FROM ("
					+ "	          SELECT *"
					+ "             FROM T_GGC_HOTDEAL"
					+ "            WHERE HOTDEAL_NM LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
					+ "              AND HOTDEAL_USE_YN = 'N') A", nativeQuery=true)
	Page<Hotdeal> getNListName(@Param("hotdeal") Hotdeal hotdeal, Pageable pageable);
	
	
	@Query (value=" SELECT *"
			+ "       FROM T_GGC_HOTDEAL"
			+ "      WHERE HOTDEAL_DESC LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
			+ "         OR HOTDEAL_SUMMARY LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
			+ "        AND HOTDAL_USE_YN = 'N'",
			countQuery="   SELECT COUNT(*)"
					+ "      FROM("
					+ "           SELECT * "
					+ "             FROM T_GGC_HOTDEAL"
					+ "            WHERE HOTDEAL_DESC LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
					+ "               OR HOTDEAL_SUMMARY LIKE CONCAT('%', :#{#hotdeal.searchKeyword}, '%')"
					+ "              AND HOTDEAL_USE_YN = 'N') A", nativeQuery=true)
	Page<Hotdeal> getNListContent(@Param("hotdeal") Hotdeal hotdeal, Pageable pageable);
	
}
