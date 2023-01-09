package com.gogi1000.datecourse.repository;

import com.gogi1000.datecourse.entity.DatecourseMenu;
import com.gogi1000.datecourse.entity.DatecourseMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DatecourseMenuRepository extends JpaRepository<DatecourseMenu, DatecourseMenuId> {
    // @Query: 원하는 쿼리를 작성할 수 있는 어노테이션
    // nativeQuery: true로 설정하면 원하는 대로 쿼리 작성, 메소드명도 JPA 규칙에서 벗어날 수 있다.
    // value: 쿼리 작성
    // 데이트 코스 메뉴 테이블의 (DATECOURSE_MENU_NO MAX 값 + 1) 조회_세혁
    @Query(value="SELECT IFNULL(MAX(M.DATECOURSE_MENU_NO), 0) + 1 FROM T_GGC_DATECOURSE_MENU M WHERE M.DATECOURSE_NO =:datecourseNo", nativeQuery=true)
    // ServiceImpl에서 넘겨주는 파라미터의 변수명이 받아주는 변수의 이름과 다를 때, 해당 파라미터이름을 명시
    // 매퍼나 리포지토리에 여러 개의 파라미터를 보낼 때 @Param 어노테이션 사용!
    int getMaxDatecourseMenuNo(@Param("datecourseNo") int datecourseNo);


    // 데이트 코스 메뉴 리스트 조회_세혁
    List<DatecourseMenu> findByDatecourseNo(int datecourseNo);
}
