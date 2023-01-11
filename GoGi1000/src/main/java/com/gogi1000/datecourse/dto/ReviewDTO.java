package com.gogi1000.datecourse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
	private int datecourseNo;
	private int reviewNo;
	private String reviewerId;
	private String reviewComment;
	private String reviewRgstDate;
	private String reviewModfDate;
	private String searchConditionCmt;
	private String searchKeywordCmt;
	
}
