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
import lombok.NoArgsConstructor;

@Entity
@Table(name="T_GGC_USER")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Data
public class User {
	@Id
	private String userId;
	
	private String userPw;
	private String userNm;
	private int userAge;
	private String userTel;
	private String userMail;
	private String userArea;
	private String userAddr1;
	private String userAddr2;
	@Column
	@ColumnDefault("'ROLE_USER")
	private String userType;
	private LocalDateTime userRgstDate = LocalDateTime.now();
	private LocalDateTime userModfDate = LocalDateTime.now();
	@Column
	@ColumnDefault("'Y")
	private String userUseYn;
}
