package com.gogi1000.datecourse.repository;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Like;
import com.gogi1000.datecourse.entity.LikeId;

@Transactional
public interface LikeRepository extends JpaRepository<Like, LikeId> {
	
	@Query(value="SELECT A.*, B.DATECOURSE_NM, B.DATECOURSE_CNT, B.DATECOURSE_DESC,"
			+ " C.LIKE_CNT"
			+ " FROM T_GGC_LIKE A, T_GGC_DATECOURSE B,"
			+ " ("
			+ " 	SELECT COUNT(*) AS LIKE_CNT"
			+ " 	, DATECOURSE_NO"
			+ " 	FROM T_GGC_LIKE C"
			+ " 	GROUP BY DATECOURSE_NO"
			+ " ) C"
			+ " WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " AND A.DATECOURSE_NO = C.DATECOURSE_NO"
			+ " AND A.USER_ID = 'test_user'"
			+ " GROUP BY DATECOURSE_NO",
			countQuery="SELECT COUNT(*)"
			+ "	FROM ("
			+ "			SELECT A.*, B.DATECOURSE_NM, B.DATECOURSE_CNT, B.DATECOURSE_DESC, "
			+ "			C.LIKE_CNT"
			+ "				FROM T_GGC_LIKE A, T_GGC_DATECOURSE B, ("
			+ "				SELECT COUNT(*) AS LIKE_CNT"
			+ " 			, DATECOURSE_NO"
			+ " 			FROM T_GGC_LIKE C"
			+ " 			GROUP BY DATECOURSE_NO"
			+ "			) C"	
			+ "				WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " 			AND A.DATECOURSE_NO = C.DATECOURSE_NO"
			+ " 			AND A.USER_ID = 'test_user'"
			+ "				GROUP BY DATECOURSE_NO"
			+ ") D"
			, nativeQuery=true)
	Page<CamelHashMap> mypageLikeList(Pageable pageable);

}
