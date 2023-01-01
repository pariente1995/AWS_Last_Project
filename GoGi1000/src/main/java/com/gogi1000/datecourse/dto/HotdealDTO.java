package com.gogi1000.datecourse.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotdealDTO {	
	private int hotdealNo;
	private String hotdealNm;
	private String hotdealEndDate;
	private String hotdealOfficialSite;
	private String hotdealPrice;
	private String hotdealSalerate;
	private String hotdealTel;
	private String hotdealDesc;
	private String hotdealRgstDate;
	private String hotdealModfDate;
	private String hotdealUseYn;
	private String searchCondition;
	private String searchKeyword;
}
