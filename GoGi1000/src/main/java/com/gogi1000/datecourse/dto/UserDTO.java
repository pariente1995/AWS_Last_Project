package com.gogi1000.datecourse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	private String userId;
	private String userPw;
	private String userNm;
	private int userAge;
	private String userTel;
	private String userMail;
	private String userArea;
	private String userAddr1;
	private String userAddr2;
	private String userType;
	private String userRgstDate;
	private String userModfDate;
	private String userUseYn;
}
