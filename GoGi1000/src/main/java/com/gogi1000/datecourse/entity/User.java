package com.gogi1000.datecourse.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="T_GGC_USER")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Setter
@Getter
public class User {
	@Id
	private String userId;
	
//	@Column(nullable = false)
	private String userPw;
	
//	@Column(nullable = false)
	private String userNm;
	
//	@Column(nullable = false)
	private int userAge;
	
//	@Column(nullable = false)
	private String userTel;
	
//	@Column(nullable = false)
	private String userMail;
	
//	@Column(nullable = false)
	private String userArea;
	
//	@Column(nullable = false)
	private String userAddr1;
	
//	@Column(nullable = false)
	private String userAddr2;
	
//	@Column(nullable = false)
	private String userType;
	
//	@Column(nullable = false)
	private LocalDateTime userRgstDate = LocalDateTime.now();
	
//	@Column(nullable = false)
	private LocalDateTime userModfDate = LocalDateTime.now();
	
//	@Column(nullable = false)
	private String userUseYn;
}
