package com.gogi1000.datecourse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="T_GGC_DATECOURSE_MENU")
@Data
@IdClass(DatecourseMenuId.class) // 복합키의 경우, 복합키를 가지고 있는 클래스 만들어 준다.
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 매개변수를 받는 생성자
@Builder // 객체 생성
@DynamicInsert // insert 구문이 생성될 때 null 값인 컬럼은 배제하고 구문 생성
@DynamicUpdate // update 변경되지 않은 값들을 제외하고 구문 생성
public class DatecourseMenu {
    @Id
    private int datecourseNo;
    @Id
    private int datecourseMenuNo;
    private String datecourseMenuType;
    private String datecourseMenuNm;
    private String datecourseMenuPrice;
    private LocalDateTime datecourseMenuRgstDate = LocalDateTime.now();
    private LocalDateTime datecourseMenuModfDate = LocalDateTime.now();
    private String datecourseMenuUseYn = "Y";
}
