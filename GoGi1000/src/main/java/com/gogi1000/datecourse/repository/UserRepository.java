package com.gogi1000.datecourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gogi1000.datecourse.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
		//SELECT * FROM T_GGC_USER
		//	WHERE USER_ID = :userId
		//	  AND USER_PW = :userPw
		User findByUserIdAndUserPw(
				@Param("userId") String userId, 
				@Param("userPw") String userPw);
}
