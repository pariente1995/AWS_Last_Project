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
import com.gogi1000.datecourse.entity.Review;
import com.gogi1000.datecourse.entity.ReviewId;

@Transactional
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
	
	// 리뷰 넘버 부여_장찬영
	@Query(value ="SELECT IFNULL(MAX(A.REVIEW_NO), 0) + 1 "
			+ "      FROM T_GGC_REVIEW A "
			+ "		WHERE A.DATECOURSE_NO = :datecourseNo", 
			nativeQuery = true)
	int getNextReviewNo(@Param("datecourseNo") int datecourseNo);
	
	// 상세 리뷰 출력(모달)_장찬영
	@Query(value="SELECT A.* "
			+ "		FROM T_GGC_REVIEW A"
			+ "	   WHERE A.DATECOURSE_NO = :#{#review.datecourseNo} "
			+ "		 AND A.REVIEW_NO = :#{#review.reviewNo}", nativeQuery=true)
	Review selectModal(@Param("review") Review review);
	
	// 화면 이동 시, 검색 기능을 사용하지 않을 경우 모든 리뷰 리스트 출력_장찬영
	@Query(value="SELECT A.*, B.DATECOURSE_NM"
			+ "		FROM T_GGC_REVIEW A, T_GGC_DATECOURSE B"
			+ "	   WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " ORDER BY A.REVIEW_NO",
			countQuery="SELECT COUNT(*)"
			+ " 		  FROM ("
			+ "						SELECT A.*, B.DATECOURSE_NM"
			+ "						  FROM T_GGC_REVIEW A, T_GGC_DATECOURSE B"
			+ "						 WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " 				  ORDER BY A.REVIEW_NO"
			+ " 			   ) C",
			nativeQuery=true)
	Page<CamelHashMap> getReviewList(Pageable pageable);
	
	
	// 전체 검색을 통해 리스트 출력_장찬영
	@Query(value="SELECT A.*"
			+ " 	FROM ("
			+ " 			SELECT R.*, D.DATECOURSE_NM"
			+ " 			  FROM T_GGC_REVIEW R, T_GGC_DATECOURSE D"
			+ " 			 WHERE R.DATECOURSE_NO = D.DATECOURSE_NO"
			+ " 		 ) A"
			+ "    WHERE 1=0"
			+ "   	  OR A.DATECOURSE_NM LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " 	  OR A.REVIEWER_ID LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " 	  OR A.REVIEW_COMMENT LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')",
			countQuery="SELECT COUNT(*)"
			+ " 		  FROM ("
			+ " 					SELECT A.*"
			+ " 		  			  FROM ("
			+ " 								SELECT R.*, D.DATECOURSE_NM"
			+ " 			  		  			  FROM T_GGC_REVIEW R, T_GGC_DATECOURSE D"
			+ " 			 		 			 WHERE R.DATECOURSE_NO = D.DATECOURSE_NO"
			+ " 		 	   			   ) A"
			+ "   	     			 WHERE 1=0"
			+ "   	  					OR A.DATECOURSE_NM LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " 	 					OR A.REVIEWER_ID LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " 	  					OR A.REVIEW_COMMENT LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " 			   ) B",
			nativeQuery=true)
	Page<CamelHashMap> getReviewListAll(@Param("review") Review review, Pageable pageable);
	
	// 이름 검색을 통해 리스트 출력_장찬영
	@Query(value="SELECT A.*, B.DATECOURSE_NM"
			+ "		FROM T_GGC_REVIEW A, T_GGC_DATECOURSE B"
			+ "	   WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ "   	 AND B.DATECOURSE_NM LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " ORDER BY A.REVIEW_NO",
			countQuery="SELECT COUNT(*)"
			+ "   		  FROM ("
			+ " 					SELECT A.* B.DATECOURSE_NM"
			+ "						  FROM T_GGC_REVIEW A, T_GGC_DATECOURSE B"
			+ "						 WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " 					   AND B.DATECOURSE_NM LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " 				  ORDER BY A.REVIEW_NO"
			+ " 			   ) C",
			nativeQuery=true)
	Page<CamelHashMap> getReviewListName(@Param("review") Review review, Pageable pageable);
	
	// 유저 아이디 검색을 통해 리스트 출력
	@Query(value="SELECT A.*, B.DATECOURSE_NM"
			+ "		FROM T_GGC_REVIEW A, T_GGC_DATECOURSE B"
			+ "	   WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " 	 AND A.REVIEWER_ID LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " ORDER BY A.REVIEW_NO",
			countQuery="SELECT COUNT(*)"
			+ " 		  FROM ("
			+ " 					SELECT A.*, B.DATECOURSE_NM"
			+ "						  FROM T_GGC_REVIEW A, T_GGC_DATECOURSE B"
			+ "						 WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " 					   AND A.REVIEWER_ID LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " 				  ORDER BY A.REVIEW_NO"
			+ " 			   ) C",
			nativeQuery=true)
	Page<CamelHashMap> getReviewListId(@Param("review") Review review, Pageable pageable);
	
	// 리뷰 내용 검색을 통해 리스트 출력_장찬영
	@Query(value="SELECT A.*, B.DATECOURSE_NM"
			+ "		FROM T_GGC_REVIEW A, T_GGC_DATECOURSE B"
			+ "	   WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " 	 AND A.REVIEW_COMMENT LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " ORDER BY A.REVIEW_NO",
			countQuery="SELECT COUNT(*)"
			+ " 		  FROM ("
			+ " 					SELECT A.*, B.DATECOURSE_NM"
			+ "						  FROM T_GGC_REVIEW A, T_GGC_DATECOURSE B"
			+ "						 WHERE A.DATECOURSE_NO = B.DATECOURSE_NO"
			+ " 					   AND A.REVIEW_COMMENT LIKE CONCAT('%', :#{#review.searchKeywordCmt}, '%')"
			+ " 				  ORDER BY A.REVIEW_NO"
			+ " 			   ) C",
			nativeQuery=true)
	Page<CamelHashMap> getReviewListComment(@Param("review") Review review, Pageable pageable);
	
	
	// 상세 게시글 리뷰리스트 출력_장찬영
	@Query(value="SELECT A.*"
			+ "		FROM T_GGC_REVIEW A"
			+ "	   WHERE A.DATECOURSE_NO = :datecourseNo"
			+ " ORDER BY A.REVIEW_RGST_DATE DESC",
			nativeQuery = true)
	List<Review> getCommentList(@Param("datecourseNo") int datecourseNo);

	// 데이트 코스 리뷰 삭제_세혁
	@Modifying
	@Query(value="DELETE FROM T_GGC_REVIEW"
			+ "    WHERE DATECOURSE_NO = :datecourseNo"
			, nativeQuery = true)
	void deleteByDatecourseNo(@Param("datecourseNo") int datecourseNo);
}
