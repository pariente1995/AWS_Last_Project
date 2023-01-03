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
	Page<Hotdeal> findByHotdealNmContaining(String searchKeyword, Pageable pageable);
	Page<Hotdeal> findByHotdealDescContaining(String searchKeyword, Pageable pageable);
	Page<Hotdeal> findByHotdealNmContainingOrHotdealDescContaining(String searchKeyword1, String searchKeyword2, Pageable pageable);
	
	
	@Modifying
	@Query(value="UPDATE T_GGC_HOTDEAL SET HOTDEAL_USE_YN = 'N' WHERE HOTDEAL_NO = :hotdealNo", nativeQuery=true)
	void updateHotdealUseYn(@Param("hotdealNo") int hotdealNo);
	
	
	  /* 
	   	메인에서 핫딜리스트 조회
	   	JOIN T_GGC_IMAGE 하여, B.IMAGE_NM으로 이미지 받아옴
	   	HOTDEAL_NO로 오름차순 정렬
	 */
	@Modifying
	@Query(value = "SELECT A.*, "
			+ "			   B.IMAGE_NM"
			+"		  FROM T_GGC_HOTDEAL A"
			+"		  JOIN T_GGC_IMAGE B"
			+"		    ON A.HOTDEAL_NO = B.REFERENCE_NO"
			+"		 WHERE B.IMAGE_GROUP = 'E0002'"
			+"		 LIMIT 10",
			nativeQuery=true)
	List<CamelHashMap> getHotdealDatecourseList();
	
	// 메인에서 핫딜 상세 페이지 조회
	@Modifying
	@Query(value = "SELECT *"
			+"		  FROM T_GGC_HOTDEAL"
			+"		 WHERE HOTDEAL_NO = :hotdealNo",
			nativeQuery=true)
	Hotdeal getHotdeal(@Param("hotdealNo") int hotdealNo);
	
	
	
}
