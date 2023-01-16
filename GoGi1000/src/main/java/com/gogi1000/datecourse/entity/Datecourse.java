package com.gogi1000.datecourse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
//@DynamicInsert // insert 구문이 생성될 때 null 값인 컬럼은 배제하고 구문 생성
@DynamicUpdate // update 변경되지 않은 값들을 제외하고 구문 생성
public class Datecourse {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "DatecourseSequenceGenerator"
    )
    private int datecourseNo;

    @Column(nullable = false)
    private String datecourseNm;

    @Column(nullable = false)
    private String datecourseArea;

    @Column(nullable = false)
    private String datecourseCategory;

    @Column(nullable = false)
    private String datecourseSummary;

    @Column(nullable = false)
    private String datecourseDesc;

    @Column(nullable = false)
    private String datecourseAddr;

    private String datecourseInoutYn;
    private String datecourseFoodType;

    @Column(nullable = false)
    private String datecourseTel;

    private String datecourseOfficialSite;
    private String datecourseParkingYn;
    private String datecoursePetYn;
    private int datecourseCnt = 0;

    @Column(nullable = false)
    private LocalDateTime datecourseRgstDate;

    @Column(nullable = false)
    private LocalDateTime datecourseModfDate;

    @Column(nullable = false)
    private String datecourseUseYn;

    @Transient
    private String searchKeyword;

    /* 카테고리 선택에 따른 데이트 코스 조회 화면에서 가격대, 정렬 조회조건_세혁 */
    @Transient
    private int datecourseMinPrice;

    @Transient
    private int datecourseMaxPrice;

    @Transient
    private String orderCondition;
}
