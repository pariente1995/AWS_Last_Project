package com.gogi1000.datecourse.service.hotdeal.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gogi1000.datecourse.common.CamelHashMap;
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
		
		return datecourseImageRepository.findByHotdealNo(hotdealNo);
	}
	
	@Override
	public Hotdeal updateHotdeal(Hotdeal hotdeal, List<CamelHashMap> uFileList){
		System.out.println("test1");
		
		hotdealRepository.save(hotdeal);
		
		System.out.println("test2");
		
		hotdealRepository.flush();
		
		
		if(uFileList.size() > 0) {
			for(int i=0; i < uFileList.size(); i++) {
				if(uFileList.get(i).get("imgFileStatus").toString().equals("U")) {
					System.out.println("22222222222222222");
					System.out.println(Integer.valueOf(uFileList.get(i).get("imageNo").toString()));
					System.out.println(Integer.parseInt(uFileList.get(i).get("imageNo").toString()));
					DatecourseImage uDatecourseImage = DatecourseImage.builder()
																		.imageGroup("E0002")
																		 .referenceNo(hotdeal.getHotdealNo())
																		 .imageNo(Integer.valueOf(uFileList.get(i).get("imageNo").toString()))
																		 .imageNm(uFileList.get(i).get("imageNm").toString())
																		 .imageOriginNm(uFileList.get(i).get("imageOriginNm").toString())
																		 .imageExt(uFileList.get(i).get("imageExt").toString())
																		 .imagePath(uFileList.get(i).get("imagePath").toString())
																		 .imageRgstDate(LocalDateTime.now())
																		 .imageModfDate(LocalDateTime.now())
																		 .build();
					System.out.println(uDatecourseImage);
					datecourseImageRepository.save(uDatecourseImage);
					

				} else if(uFileList.get(i).get("imgFileStatus").toString().equals("D")){
					
					DatecourseImage dDatecourseImage = DatecourseImage.builder()
							 										 .imageGroup("E0002")
							 										 .referenceNo(hotdeal.getHotdealNo())
							 										 .imageNo(Integer.valueOf((uFileList.get(i).get("imageNo").toString())))
							 										 .build();
					
					System.out.println("dDatecourseImage" + dDatecourseImage);
					datecourseImageRepository.delete(dDatecourseImage);
					
					
				} else if(uFileList.get(i).get("imgFileStatus").toString().equals("I")) {
					System.out.println("222222222222222222");
					int imageNo = datecourseImageRepository.getMaxImageNo("E0002", hotdeal.getHotdealNo());
					System.out.println(imageNo);
					uFileList.get(i).put("image_No", imageNo);
					System.out.println(uFileList);
					
					
					DatecourseImage iDatecourseImage = DatecourseImage.builder()
																	 .imageGroup("E0002")
																	 .referenceNo(Integer.valueOf(uFileList.get(i).get("referenceNo").toString()))
																	 .imageNo(Integer.valueOf(uFileList.get(i).get("imageNo").toString()))
																	 .imageNm(uFileList.get(i).get("imageNm").toString())
																	 .imageOriginNm(uFileList.get(i).get("imageOriginNm").toString())
																	 .imageExt(uFileList.get(i).get("imageExt").toString())
																	 .imagePath(uFileList.get(i).get("imagePath").toString())
																	 .imageRgstDate(LocalDateTime.now())
																	 .imageModfDate(LocalDateTime.now())
																	 .build();
					
					System.out.println("iDatecourseImage" + iDatecourseImage);
					datecourseImageRepository.save(iDatecourseImage);
				}
			}	
		}
		datecourseImageRepository.flush();
		
		return hotdeal;
	}
	/* 핫딜 리스트 조회 */
	@Override
	public Page<Hotdeal> getSelectPageHotdealList(Hotdeal hotdeal, Pageable pageable) {
		if(hotdeal.getSelectOption().equals("ALL")) {
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
	} else if(hotdeal.getSelectOption().equals("Y")) {
		if(hotdeal.getSearchKeyword() != null && !hotdeal.getSearchKeyword().equals("")) {	
			if(hotdeal.getSearchCondition().equals("ALL")) {
				return hotdealRepository.getYListAll(hotdeal,pageable);
			} else if(hotdeal.getSearchCondition().equals("NAME")) {
				return hotdealRepository.getYListName(hotdeal, pageable);
			} else if(hotdeal.getSearchCondition().equals("DESC")) {
				return hotdealRepository.getYListContent(hotdeal, pageable);
			} else {
				return hotdealRepository.getYList(pageable);
			}
		} else {
			return hotdealRepository.getYList(pageable);
		}
	} else if(hotdeal.getSelectOption().equals("N")) {
		if(hotdeal.getSearchKeyword() != null && !hotdeal.getSearchKeyword().equals("")) {	
			if(hotdeal.getSearchCondition().equals("ALL")) {
			return	hotdealRepository.getNListAll(hotdeal, pageable);
			} else if(hotdeal.getSearchCondition().equals("NAME")) {
			return	hotdealRepository.getNListName(hotdeal, pageable);
			} else if(hotdeal.getSearchCondition().equals("DESC")) {
			return	hotdealRepository.getNListContent(hotdeal, pageable);
			} else {
			return 	hotdealRepository.getNList(pageable);
			}
		} else {
			return hotdealRepository.getNList(pageable);
		}
	} else {
		 return hotdealRepository.findAll(pageable);
	}
  }
}
