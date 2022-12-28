package com.gogi1000.datecourse.dto;

import java.util.List;

public class ResponseDTO<T> {
	private List<T> items;
	
	private T item;
	
	private String errorMessage;
	
	private int statusCode;
}