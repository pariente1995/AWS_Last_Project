package com.gogi1000.datecourse.entity;

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
@Table(name="t_ggc_like")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert 
@DynamicUpdate
@IdClass(LikeId.class)
public class Like {
	@Id
	private String userId;
	@Id
	private int datecourseNo;
}
