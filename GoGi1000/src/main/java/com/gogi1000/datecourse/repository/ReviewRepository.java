package com.gogi1000.datecourse.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Review;
import com.gogi1000.datecourse.entity.ReviewId;

@Transactional
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
	
	@Query(value = "SELECT IFNULL(MAX(A.REVIEW_NO), 0) + 1 FROM T_GGC_REVIEW A WHERE A.DATECOURSE_NO = :datecourseNo", nativeQuery = true)
	int getNextReviewNo(@Param("datecourseNo") int datecourseNo);
	
	@Query(value="SELECT A.* FROM T_GGC_REVIEW A"
				+	" WHERE A.DATECOURSE_NO = :#{#review.datecourseNo} AND A.REVIEW_NO = :#{#review.reviewNo}", nativeQuery=true)
	Review selectModal(@Param("review") Review review);

	// void deleteAll(List<Map<String, Integer>> resultList);
	
	@Query(value="SELECT A.*"
			+ "	 , B.DATECOURSE_NM"
			+ "	FROM T_GGC_REVIEW A"
			+ "	   , T_GGC_DATECOURSE B"
			+ "	WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " ORDER BY A.REVIEW_NO",
			countQuery="SELECT COUNT(*)"
			+ " FROM ("
			+ "			SELECT A.*"
			+ "	 		, B.DATECOURSE_NM"
			+ "			FROM T_GGC_REVIEW A"
			+ "	   		, T_GGC_DATECOURSE B"
			+ "			WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " 		ORDER BY A.REVIEW_NO"
			+ " ) C",
			nativeQuery=true)
	Page<CamelHashMap> getReviewList(Pageable pageable);
	
	@Query(value="SELECT A.*"
			+ "	 , B.DATECOURSE_NM"
			+ "	FROM T_GGC_REVIEW A"
			+ "	   , ("
			+ " 		SELECT BB.*"
			+ " 		FROM T_GGC_DATECOURSE BB"
			+ " 		WHERE 1 = 0"
			+ " 		OR BB.DATECOURSE_NM LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " ) B"
			+ "	WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " OR A.REVIEWER_ID LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " OR A.REVIEW_COMMENT LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " ORDER BY A.REVIEW_NO",
			countQuery="SELECT COUNT(*)"
			+ " FROM ("
			+ " 		SELECT A.*"
			+ "	 		, B.DATECOURSE_NM"
			+ "			FROM T_GGC_REVIEW A"
			+ "	   		, ("
			+ " 			SELECT BB.*"
			+ " 			FROM T_GGC_DATECOURSE BB"
			+ " 			WHERE 1 = 0"
			+ " 			OR BB.DATECOURSE_NM LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " 		) B"
			+ "			WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " 		OR A.REVIEWER_ID LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " 		OR A.REVIEW_COMMENT LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " 		ORDER BY A.REVIEW_NO"
			+ " ) C",
			nativeQuery=true)
	Page<CamelHashMap> getReviewListAll(@Param("review") Review review, Pageable pageable);
	
	@Query(value="SELECT A.*"
			+ "	 , B.DATECOURSE_NM"
			+ "	FROM T_GGC_REVIEW A"
			+ "	   , T_GGC_DATECOURSE B"
			+ "	WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " AND B.DATECOURSE_NM LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " ORDER BY A.REVIEW_NO",
			countQuery="SELECT COUNT(*)"
					+ " FROM ("
					+ " 		SELECT A.*"
					+ "	 , B.DATECOURSE_NM"
					+ "	FROM T_GGC_REVIEW A"
					+ "	   , T_GGC_DATECOURSE B"
					+ "	WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
					+ " AND B.DATECOURSE_NM LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
					+ " ORDER BY A.REVIEW_NO"
					+ " ) C",
			nativeQuery=true)
	Page<CamelHashMap> getReviewListName(@Param("review") Review review, Pageable pageable);
	
	@Query(value="SELECT A.*"
			+ "	 , B.DATECOURSE_NM"
			+ "	FROM T_GGC_REVIEW A"
			+ "	   , T_GGC_DATECOURSE B"
			+ "	WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " AND A.REVIEWER_ID LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " ORDER BY A.REVIEW_NO",
			countQuery="SELECT COUNT(*)"
			+ " FROM ("
			+ " 		SELECT A.*"
			+ "	 		, B.DATECOURSE_NM"
			+ "			FROM T_GGC_REVIEW A"
			+ "	   		, T_GGC_DATECOURSE B"
			+ "			WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " 		AND A.REVIEWER_ID LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " 		ORDER BY A.REVIEW_NO"
			+ " ) C",
			nativeQuery=true)
	Page<CamelHashMap> getReviewListId(@Param("review") Review review, Pageable pageable);
	
	@Query(value="SELECT A.*"
			+ "	 , B.DATECOURSE_NM"
			+ "	FROM T_GGC_REVIEW A"
			+ "	   , T_GGC_DATECOURSE B"
			+ "	WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " AND A.REVIEW_COMMENT LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " ORDER BY A.REVIEW_NO",
			countQuery="SELECT COUNT(*)"
			+ " FROM ("
			+ " 		SELECT A.*"
			+ "	 		, B.DATECOURSE_NM"
			+ "			FROM T_GGC_REVIEW A"
			+ "	   		, T_GGC_DATECOURSE B"
			+ "			WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " 		AND A.REVIEW_COMMENT LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " 		ORDER BY A.REVIEW_NO"
			+ " ) C",
			nativeQuery=true)
	Page<CamelHashMap> getReviewListComment(@Param("review") Review review, Pageable pageable);

}
