package com.gogi1000.datecourse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 매개변수를 받는 생성자
@Builder // 객체 생성
public class DatecourseHoursDTO {
    private int datecourseNo;
    private int datecourseHoursNo;
    private String datecourseHoursInfo;
    // 자바스크립트, 자바단까지는 String 으로 처리 후 DB 에서 Date 형식으로 변환해준다.
    // 자바 -> 자바스크립트, 자바스크립트 -> 자바로 넘길 때 Date 형식 처리가 매우 불편하다.
    private String datecourseHoursRgstDate;
    private String datecourseHoursModfDate;
    private String datecourseHoursUseYn;
    private String datecourseHoursStatus;
    private String newDatecourseHoursInfo;
}
