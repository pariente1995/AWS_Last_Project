package com.gogi1000.datecourse.service.datecourse;

import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.entity.Image;

import java.util.List;

public interface DatecourseService {
    // 데이트 코스 등록
    void insertDatecourse(Datecourse datecourse, List<Image> uploadImageList);
}