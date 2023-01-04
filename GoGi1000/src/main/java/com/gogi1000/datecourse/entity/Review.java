package com.gogi1000.datecourse.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_ggc_review")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert 
@DynamicUpdate
@IdClass(ReviewId.class)
public class Review {
	@Id
	private int datecourseNo;
	@Id
	private int reviewNo;
	@Column(nullable=false)
	private String reviewerId;
	@Column(nullable=false)
	private String reviewComment;
	@Column(nullable=false)
	private LocalDateTime reviewRgstDate = LocalDateTime.now();
	@Column(nullable=false)
	private LocalDateTime reviewModfDate = LocalDateTime.now();
	@Transient
	private String searchConditionCmt;
	@Transient
	private String searchKeywordCmt;

}
