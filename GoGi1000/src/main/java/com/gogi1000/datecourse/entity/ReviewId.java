package com.gogi1000.datecourse.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReviewId implements Serializable {
	private int datecourseNo;
	private int reviewNo;
}
