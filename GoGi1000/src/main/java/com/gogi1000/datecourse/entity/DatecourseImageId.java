package com.gogi1000.datecourse.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DatecourseImageId implements Serializable {
    private String imageGroup;
    private int referenceNo;
    private int imageNo;
}
