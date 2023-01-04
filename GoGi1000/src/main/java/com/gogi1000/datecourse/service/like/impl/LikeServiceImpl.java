package com.gogi1000.datecourse.service.like.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.entity.Like;
import com.gogi1000.datecourse.entity.LikeId;
import com.gogi1000.datecourse.repository.LikeRepository;
import com.gogi1000.datecourse.service.like.LikeService;

@Service
public class LikeServiceImpl implements LikeService {
	
	@Autowired
	LikeRepository likeRepository;
	
	@Override
	public Page<CamelHashMap> mypageLikeList(Pageable pageable) {
		return likeRepository.mypageLikeList(pageable);
	}
	
	@Override
	public void deleteLike(LikeId likeId) {
		System.out.println(likeId);
		
		likeRepository.deleteById(likeId);
	}
	
	@Override
	public void insertLike(Like like) {
		likeRepository.save(like);
	}
}
