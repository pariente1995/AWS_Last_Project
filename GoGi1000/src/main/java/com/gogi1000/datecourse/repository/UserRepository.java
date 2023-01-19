package com.gogi1000.datecourse.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gogi1000.datecourse.entity.User;

@Transactional
public interface UserRepository extends JpaRepository<User, String> {
		//SELECT * FROM T_GGC_USER
		//	WHERE USER_ID = :userId
		//	  AND USER_PW = :userPw
		Optional<User> findByUserNmAndUserMail(
				@Param("userNm") String userNm,
				@Param("userMail") String userMail);
	
		Optional<User> findByUserIdAndUserPw(
				@Param("userId") String userId, 
				@Param("userPw") String userPw);
		
		Optional<User> findByUserIdAndUserMail(@Param("userId") String userId, @Param("userMail") String userMail);
		
		@Modifying
		@Query(value="UPDATE T_GGC_USER"
				+ "		SET USER_PW = :userPw"
				+ "		WHERE USER_ID = :userId", nativeQuery=true)
		int updateTempPw(@Param("userId") String userId, @Param("userPw") String userPw);
		
		//Optional<User> newPw(String userId, String userPw);
		
//		@Modifying
//		@Query(value="UPDATE T_GGC_USER"
//				+ "		SET USER_PW = :userPw"
//				+ "		WHERE USER_ID = :userId", nativeQuery=true)
//		void updatenewPw(@Param("userId") String userId, @Param("userPw") String userPw);

		@Query(value="SELECT COUNT(*) FROM T_GGC_USER WHERE USER_NM = :userNm", nativeQuery=true)
		int getUserNmCnt(@Param("userNm") String userNm);

		@Query(value="SELECT *"
				+ "		FROM T_GGC_USER"
				+ "	   WHERE USER_ID = :#{#user.userId}",
				nativeQuery=true)
		void getEditMypage(@Param("user") User user);

		@Modifying
		@Query(value="UPDATE T_GGC_USER"
				+ "SET USER_PW = :#{#user.userPw},"
				+ "    USER_NM = :#{#user.userNm},"
				+ "    USER_AGE = :#{#user.userAge},"
				+ "    USER_AREA = :#{#user.userArea},"
				+ "    USER_RGST_DATE = :#{#user.userRgstDate},"
				+ "    USER_ADDR1 = :#{#user.userAddr1},"
				+ "    USER_ADDR2 = :#{#user.userAddr2},"
				+ "    USER_TYPE = :#{#user.userType},"
				+ "    USER_TEL = :#{#user.userTel},"
				+ "    USER_MODF_DATE = :#{#user.userModfDate},"
				+ "    USER_USE_YN = :#{#user.userUseYn}"
				+ "	   WHERE USER_ID = :#{#user.userId}", nativeQuery=true)
		void updateUserInfo(@Param("user") User user);
		
}
