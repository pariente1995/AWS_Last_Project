package com.gogi1000.datecourse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="T_GGC_IMAGE")
@Data
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 매개변수를 받는 생성자
@Builder // 객체 생성
public class Image {
    @Id
    private String imageGroup;
    @Id
    private int referenceNo;
    private int imageNo;
    private String imageNm;
    private String imageType;
    private LocalDateTime imageRgstDate = LocalDateTime.now();
    private LocalDateTime imageModfDate = LocalDateTime.now();
}
