package com.gogi1000.datecourse.service.like.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Like;
import com.gogi1000.datecourse.entity.LikeId;
import com.gogi1000.datecourse.entity.Review;
import com.gogi1000.datecourse.repository.LikeRepository;
import com.gogi1000.datecourse.service.like.LikeService;

@Service
public class LikeServiceImpl implements LikeService {
	
	@Autowired
	private LikeRepository likeRepository;
	
	// 좋아요 리스트 출력_장찬영
	@Override
	public Page<CamelHashMap> mypageLikeList(Review review, Pageable pageable) {
		return likeRepository.mypageLikeList(review, pageable);
	}
	
	// 좋아요 삭제_장찬영
	@Override
	public void deleteLike(Like like) {
		System.out.println(like);
		
		likeRepository.delete(like);
	}
	
	// 좋아요 등록_장찬영
	@Override
	public void insertLike(Like like) {
		likeRepository.save(like);
	}
	
}
