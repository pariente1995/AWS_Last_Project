package com.gogi1000.datecourse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 매개변수를 받는 생성자
@Builder // 객체 생성
public class DatecourseDTO {
    private int datecourseNo;
    private String datecourseNm;
    private String datecourseArea;
    private String datecourseCategory;
    private String datecourseSummary;
    private String datecourseDesc;
    private String datecourseAddr;
    private String datecourseInoutYn;
    private String datecourseFoodType;
    private String datecourseTel;
    private String datecourseOfficialSite;
    private String datecourseParkingYn;
    private String datecoursePetYn;
    private int datecourseCnt;
    // 자바스크립트, 자바단까지는 String 으로 처리 후 DB 에서 Date 형식으로 변환해준다.
    // 자바 -> 자바스크립트, 자바스크립트 -> 자바로 넘길 때 Date 형식 처리가 매우 불편하다.
    private String datecourseRgstDate;
    private String datecourseModfDate;
    private String datecourseUseYn;

    /* Datecourse 엔티티 @Transient 관련_세혁 */
    private String searchKeyword;
    private int datecourseMinPrice;
    private int datecourseMaxPrice;
    private String orderCondition;
}
