package com.gogi1000.datecourse.common;

import org.springframework.jdbc.support.JdbcUtils;

import java.util.HashMap;

//Map을 카멜케이스 형식으로 받아온다.
@SuppressWarnings("serial")
public class CamelHashMap extends HashMap<String, Object> {
	@Override
	public Object put(String key, Object value) {
		return super.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), value);
	}
}
