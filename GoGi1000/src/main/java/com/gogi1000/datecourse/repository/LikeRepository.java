package com.gogi1000.datecourse.repository;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Like;
import com.gogi1000.datecourse.entity.LikeId;
import com.gogi1000.datecourse.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

@Transactional
public interface LikeRepository extends JpaRepository<Like, LikeId> {
	
	// 좋아요 리스트 출력_장찬영
	@Query(value="SELECT A.*, B.DATECOURSE_NM, B.DATECOURSE_CNT, B.DATECOURSE_DESC,"
	         + " IFNULL(C.IMAGE_NM, 'NULL') AS IMAGE_NM, D.LIKE_CNT"
	         + " FROM T_GGC_LIKE A LEFT JOIN T_GGC_DATECOURSE B"
	         + " ON A.DATECOURSE_NO = B.DATECOURSE_NO"
	         + " LEFT JOIN ("
	         + "    			SELECT C.REFERENCE_NO, C.IMAGE_NM"
	         + "    			FROM T_GGC_IMAGE C"
	         + "    			WHERE C.IMAGE_NO = 1"
	         + " 				AND C.IMAGE_GROUP = 'E0001'"
	         + " ) C"
	         + " ON A.DATECOURSE_NO = C.REFERENCE_NO"
	         + " , ("
	         + " 				SELECT COUNT(*) AS LIKE_CNT"
	         + " 				, D.DATECOURSE_NO"
	         + " 				FROM T_GGC_LIKE D"
	         + " 				GROUP BY DATECOURSE_NO"
	         + " ) D"
	         + " WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
	         + " AND A.DATECOURSE_NO = D.DATECOURSE_NO"
	         + " AND A.USER_ID = :#{#review.reviewerId}"
	         + " GROUP BY DATECOURSE_NO",
	         countQuery="SELECT COUNT(*)"
	         + "   FROM ("
	         + "         	SELECT A.*, B.DATECOURSE_NM, B.DATECOURSE_CNT, B.DATECOURSE_DESC,"
	         + " 			IFNULL(C.IMAGE_ORIGIN_NM, 'NULL') AS IMAGE_ORIGIN_NM, D.LIKE_CNT"
	         + " 			FROM T_GGC_LIKE A LEFT JOIN T_GGC_DATECOURSE B"
	         + " 			ON A.DATECOURSE_NO = B.DATECOURSE_NO"
	         + " 			LEFT JOIN ("
	         + "    						SELECT C.REFERENCE_NO, C.IMAGE_ORIGIN_NM"
	         + "    						FROM T_GGC_IMAGE C"
	         + "    						WHERE C.IMAGE_NO = 1"
	         + " 							AND C.IMAGE_GROUP = 'E0001'"
	         + " 			) C"
	         + " 			ON A.DATECOURSE_NO = C.REFERENCE_NO"
	         + " 			, ("
	         + " 							SELECT COUNT(*) AS LIKE_CNT"
	         + " 							, D.DATECOURSE_NO"
	         + " 							FROM T_GGC_LIKE D"
	         + " 							GROUP BY DATECOURSE_NO"
	         + " 			) D"
	         + " 			WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
	         + " 			AND A.DATECOURSE_NO = D.DATECOURSE_NO"
	         + " 			AND A.USER_ID = :#{#review.reviewerId}"
	         + " 			GROUP BY DATECOURSE_NO"
	         + ") E"
	         , nativeQuery=true)
	   Page<CamelHashMap> mypageLikeList(@Param("review") Review review, Pageable pageable);

	// 데이트 코스 좋아요 삭제_세혁
	@Modifying
	@Query(value="DELETE FROM T_GGC_LIKE"
			+ "    WHERE DATECOURSE_NO = :datecourseNo"
			, nativeQuery = true)
	void deleteByDatecourseNo(@Param("datecourseNo") int datecourseNo);
}
