package com.gogi1000.datecourse.service.like;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Like;
import com.gogi1000.datecourse.entity.LikeId;

public interface LikeService {
	
	Page<CamelHashMap> mypageLikeList(Pageable pageable);
	
	void deleteLike(LikeId likeId);
	
	void insertLike(Like like);
}
