package com.gogi1000.datecourse.service.hotdeal.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gogi1000.datecourse.entity.DatecourseImage;
import com.gogi1000.datecourse.entity.Hotdeal;
import com.gogi1000.datecourse.repository.DatecourseImageRepository;
import com.gogi1000.datecourse.repository.HotdealRepository;
import com.gogi1000.datecourse.service.hotdeal.HotdealService;

@Service
public class HotdealServiceImpl implements HotdealService {
	@Autowired
	private HotdealRepository hotdealRepository;
	
	@Autowired
	private DatecourseImageRepository datecourseImageRepository;
	
	
	
	@Override
	public List<Hotdeal> getHotdealList(Hotdeal hotdeal) {
		return hotdealRepository.findAll();
	}
	
	
	@Override
	public void insertHotdeal(Hotdeal hotdeal, List<DatecourseImage> uploadImageList) {
		hotdealRepository.save(hotdeal);
		hotdealRepository.flush();
		
        for(DatecourseImage datecourseImage : uploadImageList) {
            // 이미지 그룹 -> 데이트 코스: E0001 핫딜: E0002
            String imageGroup = "E0002";
            
            datecourseImage.setImageGroup(imageGroup);
            datecourseImage.setReferenceNo(hotdeal.getHotdealNo());

            // 해당 imageGroup, datecourseNo 에 대한 imageNo의 MAX 값을 가져옴
            int imageNo = datecourseImageRepository.getMaxImageNo(imageGroup, hotdeal.getHotdealNo());
            datecourseImage.setImageNo(imageNo);

            datecourseImageRepository.save(datecourseImage);
        }
	}
	
	/* 핫딜 사용 여부 */
	@Override
	public void updateHotdealUseYn(int HotdealNo) {
		hotdealRepository.updateHotdealUseYn(HotdealNo);
	}
	
	/* 핫딜 상세 조회 */
	@Override
	public Hotdeal getHotdeal(int HotdealNo) {
		return hotdealRepository.findById(HotdealNo).get();
	}
	
	/* 핫딜 리스트 조회 */
	@Override
	public Page<Hotdeal> getPageHotdealList(Hotdeal hotdeal, Pageable pageable) {
		if(hotdeal.getSearchKeyword() != null && !hotdeal.getSearchKeyword().equals("")) {	
			if(hotdeal.getSearchCondition().equals("ALL")) {
				return hotdealRepository.findByHotdealNmContainingOrHotdealDescContaining
						(hotdeal.getSearchKeyword(),
						 hotdeal.getSearchKeyword(),
						 pageable);
			} else if(hotdeal.getSearchCondition().equals("NAME")) {
				return hotdealRepository.findByHotdealNmContaining(hotdeal.getSearchKeyword(), pageable);
			} else if(hotdeal.getSearchCondition().equals("DESC")) {
				return hotdealRepository.findByHotdealDescContaining(hotdeal.getSearchKeyword(), pageable);
			} else {
				return hotdealRepository.findAll(pageable);
			}
		} else {
			return hotdealRepository.findAll(pageable);
		}
	}
	@Override
	public List<DatecourseImage> getHotdealImageList(int hotdealNo) {
		Hotdeal hotdeal = Hotdeal.builder()
								 .hotdealNo(hotdealNo)
								 .build();
		
		return datecourseImageRepository.findByHotdeal(hotdeal);
	}
	
}
