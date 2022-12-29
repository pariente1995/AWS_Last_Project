package com.gogi1000.datecourse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="T_GGC_DATECOURSE")
@Data
@SequenceGenerator(
        name="DatecourseSequenceGenerator",
        sequenceName="T_GGC_DATECOURSE_SEQ",
        initialValue=1, // 시작값
        allocationSize=1 // 메모리를 통해 할당할 범위 사이즈
)
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 매개변수를 받는 생성자
@Builder // 객체 생성
@DynamicInsert // insert 구문이 생성될 때 null 값인 컬럼은 배제하고 구문 생성
@DynamicUpdate // update 변경되지 않은 값들을 제외하고 구문 생성
public class Datecourse {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "DatecourseSequenceGenerator"
    )
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
    private int datecourseCnt = 0;
    private LocalDateTime datecourseRgstDate = LocalDateTime.now();
    private LocalDateTime datecourseModfDate = LocalDateTime.now();
    private String datecourseUseYn = "Y";

    @Transient
    private String searchKeyword;
}
