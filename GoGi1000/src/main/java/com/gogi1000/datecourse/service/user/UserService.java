package com.gogi1000.datecourse.service.user;

import com.gogi1000.datecourse.entity.User;

public interface UserService {
	User join(User user);
	
	User idCheck(User user);

	User findPwd(User user);

	void sendCert(User user);

	User pwCheck(User user);

	User findId(User user);

	User nmCheck(User user);

	int getUserNmCnt(User user);

	int updateUserPw(User user);

//	User newPwd(User user);
	
	
}


