package com.gogi1000.datecourse.oauth.provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{
	Map<String, Object> attributes;
	
	public NaverUserInfo(Map<String, Object> attributes) {
		this.attributes = (Map<String, Object>)attributes.get("response");
	}

	@Override
	public String getProviderId() {
		// TODO Auto-generated method stub
		return attributes.get("id") + "";
	}

	@Override
	public String getProvider() {
		// TODO Auto-generated method stub
		return "naver";
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return attributes.get("email") + "";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return attributes.get("name") + "";
	}
}
