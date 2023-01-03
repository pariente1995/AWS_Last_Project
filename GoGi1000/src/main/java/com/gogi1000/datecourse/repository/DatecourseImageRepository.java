package com.gogi1000.datecourse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gogi1000.datecourse.entity.DatecourseImage;
import com.gogi1000.datecourse.entity.DatecourseImageId;
import com.gogi1000.datecourse.entity.Hotdeal;

public interface DatecourseImageRepository extends JpaRepository<DatecourseImage, DatecourseImageId> {
    // @Query: 원하는 쿼리를 작성할 수 있는 어노테이션
    // nativeQuery: true로 설정하면 원하는 대로 쿼리 작성, 메소드명도 JPA 규칙에서 벗어날 수 있다.
    // value: 쿼리 작성
    @Query(value="SELECT IFNULL(MAX(I.IMAGE_NO), 0) + 1 FROM T_GGC_IMAGE I WHERE I.IMAGE_GROUP =:imageGroup AND I.REFERENCE_NO =:referenceNo", nativeQuery=true)
    // ServiceImpl에서 넘겨주는 파라미터의 변수명이 받아주는 변수의 이름과 다를 때, 해당 파라미터이름을 명시
    // 매퍼나 리포지토리에 여러 개의 파라미터를 보낼 때 @Param 어노테이션 사용!
    int getMaxImageNo(@Param("imageGroup") String imageGroup, @Param("referenceNo") int referenceNo);
    
    
    @Query(value="SELECT A.*, B.HOTDEAL_NO "
    		+ "	    FROM T_GGC_IMAGE A "
    		+ "		LEFT JOIN T_GGC_HOTDEAL B ON A.REFERENCE_NO = B.HOTDEAL_NO "
    		+ "	   WHERE A.IMAGE_GROUP = 'E0002' "
    		+ "      AND A.REFERENCE_NO = B.HOTDEAL_NO "
    		+ "      AND A.IMAGE_NO = 1",
    		nativeQuery=true)
    List<DatecourseImage> findByHotdeal(Hotdeal hotdeal);
}
