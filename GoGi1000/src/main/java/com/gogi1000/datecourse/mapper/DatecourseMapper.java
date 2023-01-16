package com.gogi1000.datecourse.mapper;

import com.gogi1000.datecourse.common.CamelHashMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

// DAO 역할을 해서 DAO 파일 만들 필요 없음
// datecourse-mapper.xml로 연결해서 DB로 들어갔다가 값들을 받아서 나옴.
@Mapper
public interface DatecourseMapper {
    // 카테고리 선택에 따른 데이트 코스 리스트 조회_세혁
    List<CamelHashMap> getPageCateDatecourseList(Map<String, Object> paramMap);

    // 카테고리 선택에 따른 데이트 코스 리스트 총 개수 조회_세혁
    int getCateDatecourseListCnt(Map<String, Object> paramMap);
}
