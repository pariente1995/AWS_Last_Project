package com.gogi1000.datecourse.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="t_ggc_mydatecourse")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@IdClass(MyDatecourseId.class)
public class MyDatecourse {
	@Id
	private String userId;
	@Id
	private int datecourseNo;
	
	@Column(nullable=false)
	private LocalDateTime myDatecourseRgstDate = LocalDateTime.now();
}
