package com.gogi1000.datecourse.service.like;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Like;
import com.gogi1000.datecourse.entity.LikeId;
import com.gogi1000.datecourse.entity.Review;

public interface LikeService {
	
	// 좋아요 리스트 출력_장찬영
	Page<CamelHashMap> mypageLikeList(Review review, Pageable pageable);
	
	// 좋아요 삭제_장찬영
	void deleteLike(LikeId likeId);
	
	// 좋아요 등록_장찬영
	void insertLike(Like like);
	
}
