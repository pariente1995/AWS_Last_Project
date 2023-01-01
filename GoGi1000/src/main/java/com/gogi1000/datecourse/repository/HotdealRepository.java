package com.gogi1000.datecourse.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gogi1000.datecourse.entity.Hotdeal;

@Transactional
public interface HotdealRepository extends JpaRepository<Hotdeal, Integer> {
	Page<Hotdeal> findByHotdealNmContaining(String searchKeyword, Pageable pageable);
	Page<Hotdeal> findByHotdealDescContaining(String searchKeyword, Pageable pageable);
	Page<Hotdeal> findByHotdealNmContainingOrHotdealDescContaining(String searchKeyword1, String searchKeyword2, Pageable pageable);
	
	
	@Modifying
	@Query(value="UPDATE T_GGC_HOTDEAL SET HOTDEAL_USE_YN = 'N' WHERE HOTDEAL_NO = :hotdealNo", nativeQuery=true)
	void updateHotdealUseYn(@Param("hotdealNo") int hotdealNo);
	
}
