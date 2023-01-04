package com.gogi1000.datecourse.service.datecourse;

import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.entity.DatecourseHours;
import com.gogi1000.datecourse.entity.DatecourseImage;
import com.gogi1000.datecourse.entity.DatecourseMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DatecourseService {
    // 데이트 코스 등록
    void insertDatecourse(Datecourse datecourse, List<DatecourseHours> iDatecourseHoursList,
                          List<DatecourseMenu> iDatecourseMenuList, List<DatecourseImage> uploadImageList);

    // 데이트 코스 리스트 화면(관리자) 조회 - 페이지 번호 추가
    Page<Datecourse> getPageDatecourseList(Datecourse datecourse, Pageable pageable);

    // 데이트 코스 리스트 화면(관리자)에서 삭제 시, 데이트 코스 사용여부를 ('Y' -> 'N')으로 업데이트
    void updateDatecourseList(List<Integer> updateDatecourseList);
}