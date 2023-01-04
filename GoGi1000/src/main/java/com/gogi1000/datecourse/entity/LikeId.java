package com.gogi1000.datecourse.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class LikeId implements Serializable {
	private String userId;
	private int datecourseNo;
}
