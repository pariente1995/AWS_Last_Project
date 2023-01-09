package com.gogi1000.datecourse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="T_GGC_IMAGE")
@Data
@IdClass(DatecourseImageId.class) // 복합키의 경우, 복합키를 가지고 있는 클래스 만들어 준다.
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 매개변수를 받는 생성자
@Builder // 객체 생성
public class DatecourseImage {
    @Id
    private String imageGroup;
    @Id
    private int referenceNo;
    @Id
    private int imageNo;

    @Column(nullable = false)
    private String imageNm;

    @Column(nullable = false)
    private String imageOriginNm;

    @Column(nullable = false)
    private String imageExt;

    @Column(nullable = false)
    private String imagePath;

    @Column(nullable = false)
    private LocalDateTime imageRgstDate = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime imageModfDate = LocalDateTime.now();

    @Transient
    private String datecourseImageStatus;

    @Transient
    private String newImageNm;
}
