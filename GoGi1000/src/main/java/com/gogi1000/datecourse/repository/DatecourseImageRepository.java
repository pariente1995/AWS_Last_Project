package com.gogi1000.datecourse.repository;

import com.gogi1000.datecourse.entity.DatecourseImage;
import com.gogi1000.datecourse.entity.DatecourseImageId;
import com.gogi1000.datecourse.entity.Hotdeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DatecourseImageRepository extends JpaRepository<DatecourseImage, DatecourseImageId> {
    // @Query: 원하는 쿼리를 작성할 수 있는 어노테이션
    // nativeQuery: true로 설정하면 원하는 대로 쿼리 작성, 메소드명도 JPA 규칙에서 벗어날 수 있다.
    // value: 쿼리 작성
	// 데이트 코스 이미지 테이블의 (IMAGE_NO MAX 값 + 1) 조회_세혁
    @Query(value="SELECT IFNULL(MAX(I.IMAGE_NO), 0) + 1 FROM T_GGC_IMAGE I WHERE I.IMAGE_GROUP =:imageGroup AND I.REFERENCE_NO =:referenceNo", nativeQuery=true)
    // ServiceImpl에서 넘겨주는 파라미터의 변수명이 받아주는 변수의 이름과 다를 때, 해당 파라미터이름을 명시
    // 매퍼나 리포지토리에 여러 개의 파라미터를 보낼 때 @Param 어노테이션 사용!
    int getMaxImageNo(@Param("imageGroup") String imageGroup, @Param("referenceNo") int referenceNo);
    
    // 핫딜 상세 페이지 이미지 조회_인겸
    @Query(value="	  SELECT A.*, B.HOTDEAL_NO"
    		+ " 		FROM T_GGC_IMAGE A"
    		+ "	   		JOIN T_GGC_HOTDEAL B"
    		+ "			  ON A.REFERENCE_NO = B.HOTDEAL_NO"
    		+ "		   WHERE A.IMAGE_GROUP = 'E0002'"
    		+ "		  	 AND B.HOTDEAL_NO = :#{#hotdeal.hotdealNo}",
    		nativeQuery=true)
    List<DatecourseImage>  findByHotdeal(@Param("hotdeal") Hotdeal hotdeal);
    
    // 핫딜 리스트에서 핫딜 상세 페이지 조회_인겸
    @Query(value="SELECT *"
    		+ "	    FROM T_GGC_IMAGE  "
    		+ "	   WHERE IMAGE_GROUP = 'E0002' "
    		+ "      AND REFERENCE_NO = :hotdealNo ",
    		nativeQuery=true)
    List<DatecourseImage> findByHotdealNo(@Param("hotdealNo") int hotdealNo);
    
    /*
    @Modifying
    @Query(value="UPDATE T_GGC_IMAGE"
    		+ " SET IMAGE_NM = :#{#uDatecourseImage.imageNm},"
    		+ "	   IMAGE_ORIGIN_NM = :#{#uDatecourseImage.imageOriginNm},"
    		+ "	   IMAGE_EXT = :#{#uDatecourseImage.imageExt},"
    		+ "    IMAGE_PATH = :#{#uDatecourseImage.imagePath},"
    		+ "    IMAGE_MODF_DATE = :#{uDatecourseImage.imageModfDate}"
    		+ " WHERE REFERENCE_NO = :#{#uDatecourseImage.referenceNo}"
    		+ "	 AND IMAGE_NO = :#{#uDatecourseImage.imageNo}"
    		+ "  AND IMAGE_GROUP = :#{#uDatecourseImage.imageGroup}", nativeQuery=true)	  
    void updateHotdealImage(@Param("uDatecourseImage") DatecourseImage uDatecourseImage);*/
    


	// 데이트 코스 이미지 리스트 조회_세혁
	List<DatecourseImage> findByImageGroupAndReferenceNo(String imageGroup, int referenceNo);
	
	// 메인에서 인기 상세 리스트 조회 시, '이미지' 리스트 조회_인겸
	@Query(value =" SELECT A.*,"
    		+ "		  	   B.DATECOURSE_NO"
    		+ "		  FROM T_GGC_IMAGE A"
    		+ "		  JOIN T_GGC_DATECOURSE B"
    		+ "			ON A.REFERENCE_NO = B.DATECOURSE_NO"
    		+ "		 WHERE A.IMAGE_GROUP = 'E0001'"
    		+ "		   AND A.REFERENCE_NO = :datecourseNo",
    		nativeQuery=true)
    List<DatecourseImage> findByDatecourse(@Param("datecourseNo") int datecourseNo);

	// 데이트 코스 이미지 삭제_세혁
	@Modifying
	@Query(value="DELETE FROM T_GGC_IMAGE"
			+ "    WHERE IMAGE_GROUP = 'E0001'"
			+ "      AND REFERENCE_NO = :datecourseNo"
			, nativeQuery = true)
	void deleteByDatecourseNo(@Param("datecourseNo") int datecourseNo);
}