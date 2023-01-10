package com.gogi1000.datecourse.service.user;

import com.gogi1000.datecourse.entity.User;

public interface UserService {
	void join(User user);
	
	User idCheck(User user);

	User findPwd(User user);

	void sendCert(User user);
	
}


