package com.gogi1000.datecourse.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="T_GGC_HOTDEAL")
@SequenceGenerator(
		name="HotdealSequenceGenerator",
		sequenceName="T_GGC_HOTDEAL_SEQ",
		initialValue=1,
		allocationSize=1
)
@AllArgsConstructor
@NoArgsConstructor
//@DynamicInsert
@DynamicUpdate
@Builder
public class Hotdeal {
	@Id
	@GeneratedValue(
			strategy=GenerationType.SEQUENCE,
			generator="HotdealSequenceGenerator")
	private int hotdealNo;
	
	@Column(nullable=false)
	private String hotdealNm;
	
	@Column(nullable=false)
	private String hotdealEndDate;
	
	@Column(nullable=false)
	private String hotdealOfficialSite;
	
	@Column(nullable=false)
	private String hotdealPrice;
	
	@Column(nullable=false)
	private String hotdealSalerate;
	
	@Column(nullable=false)
	private String hotdealTel;
	
	@Column(nullable=false)
	private String hotdealDesc;
	
	@Column(nullable=false)
	private LocalDateTime hotdealRgstDate = LocalDateTime.now();
	
	@Column(nullable=false)
	private LocalDateTime hotdealModfDate = LocalDateTime.now();
	
	@Column(nullable=false)
	private String hotdealUseYn;
	
	@Transient
	private String searchCondition;
	
	@Transient
	private String searchKeyword;
}
