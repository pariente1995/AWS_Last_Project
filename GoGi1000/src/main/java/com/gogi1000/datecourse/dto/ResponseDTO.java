package com.gogi1000.datecourse.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class ResponseDTO<T> {
	private List<T> items;
	
	private Page<T> pageItems;
	
	private T item;
	
	private String errorMessage;
	
	private int statusCode;
}
