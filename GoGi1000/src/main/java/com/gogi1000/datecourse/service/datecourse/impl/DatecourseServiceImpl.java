package com.gogi1000.datecourse.service.datecourse.impl;

import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.entity.DatecourseHours;
import com.gogi1000.datecourse.entity.DatecourseImage;
import com.gogi1000.datecourse.entity.DatecourseMenu;
import com.gogi1000.datecourse.repository.DatecourseHoursRepository;
import com.gogi1000.datecourse.repository.DatecourseImageRepository;
import com.gogi1000.datecourse.repository.DatecourseMenuRepository;
import com.gogi1000.datecourse.repository.DatecourseRepository;
import com.gogi1000.datecourse.service.datecourse.DatecourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatecourseServiceImpl implements DatecourseService {
    @Autowired
    private DatecourseRepository datecourseRepository;

    @Autowired
    private DatecourseImageRepository datecourseImageRepository;

    @Autowired
    private DatecourseHoursRepository datecourseHoursRepository;

    @Autowired
    private DatecourseMenuRepository datecourseMenuRepository;

    // 데이트 코스 등록
    @Override
    public void insertDatecourse(Datecourse datecourse, List<DatecourseHours> iDatecourseHoursList,
                                 List<DatecourseMenu> iDatecourseMenuList, List<DatecourseImage> uploadImageList) {

        // 데이트 코스 등록
        datecourseRepository.save(datecourse);
        datecourseRepository.flush();

        // 데이트 코스 영업시간 등록
        for(DatecourseHours datecourseHours : iDatecourseHoursList) {
            int datecourseHoursNo = datecourseHoursRepository.getMaxDatecourseHoursNo(datecourse.getDatecourseNo());

            datecourseHours.setDatecourseNo(datecourse.getDatecourseNo());
            datecourseHours.setDatecourseHoursNo(datecourseHoursNo);

            datecourseHoursRepository.save(datecourseHours);
        }

        // 데이트 코스 메뉴 등록
        for(DatecourseMenu datecourseMenu : iDatecourseMenuList) {
            int datecourseMenuNo = datecourseMenuRepository.getMaxDatecourseMenuNo(datecourse.getDatecourseNo());

            datecourseMenu.setDatecourseNo(datecourse.getDatecourseNo());
            datecourseMenu.setDatecourseMenuNo(datecourseMenuNo);

            datecourseMenuRepository.save(datecourseMenu);
        }

        // 데이트 코스 이미지 등록
        for(DatecourseImage datecourseImage : uploadImageList) {
            // 이미지 그룹 -> 데이트 코스: E0001 핫딜: E0002
            String imageGroup = "E0001";
            
            datecourseImage.setImageGroup(imageGroup);
            datecourseImage.setReferenceNo(datecourse.getDatecourseNo());

            // 해당 imageGroup, datecourseNo 에 대한 imageNo의 MAX 값을 가져옴
            int imageNo = datecourseImageRepository.getMaxImageNo(imageGroup, datecourse.getDatecourseNo());
            datecourseImage.setImageNo(imageNo);

            datecourseImageRepository.save(datecourseImage);
        }
    }

    // 데이트 코스 리스트 조회(관리자)
    @Override
    public Page<Datecourse> getPageDatecourseList(Datecourse datecourse, Pageable pageable) {
        // 지역, 데이트 코스 분류, 검색어(데이트 코스명) 중 하나라도 입력된 경우
        if((datecourse.getDatecourseArea() != null && !datecourse.getDatecourseArea().equals("")) ||
                (datecourse.getDatecourseCategory() != null && !datecourse.getDatecourseCategory().equals("")) ||
                (datecourse.getSearchKeyword() != null && !datecourse.getSearchKeyword().equals(""))) {
            // 지역, 데이트 코스, 검색어(데이트 코스명)가 모두 입력된 경우
            if((!datecourse.getDatecourseArea().equals("ALL")) &&
                    (!datecourse.getDatecourseCategory().equals("ALL")) &&
                    (datecourse.getSearchKeyword() != null && !datecourse.getSearchKeyword().equals(""))) {
                return datecourseRepository.findByDatecourseAreaAndDatecourseCategoryAndDatecourseNmContaining(
                        datecourse.getDatecourseArea(),
                        datecourse.getDatecourseCategory(),
                        datecourse.getSearchKeyword(),
                        pageable);
            } else if(!datecourse.getDatecourseArea().equals("ALL") &&
                    !datecourse.getDatecourseCategory().equals("ALL")) {
                // 지역, 데이트 코스 분류가 선택된 경우
                return datecourseRepository.findByDatecourseAreaAndDatecourseCategory(
                        datecourse.getDatecourseArea(),
                        datecourse.getDatecourseCategory(),
                        pageable);
            } else if(!datecourse.getDatecourseArea().equals("ALL") &&
                    (datecourse.getSearchKeyword() != null && !datecourse.getSearchKeyword().equals(""))) {
                // 지역, 검색어(데이트 코스명)가 입력된 경우
                return datecourseRepository.findByDatecourseAreaAndDatecourseNmContaining(
                        datecourse.getDatecourseArea(),
                        datecourse.getSearchKeyword(),
                        pageable);
            } else if(!datecourse.getDatecourseCategory().equals("ALL") &&
                    (datecourse.getSearchKeyword() != null && !datecourse.getSearchKeyword().equals(""))) {
                // 데이트 코스 분류, 검색어(데이트 코스명)가 입력된 경우
                return datecourseRepository.findByDatecourseCategoryAndDatecourseNmContaining(
                        datecourse.getDatecourseCategory(),
                        datecourse.getSearchKeyword(),
                        pageable);
            } else if(!datecourse.getDatecourseArea().equals("ALL")) {
                // 지역만 입력된 경우
                return datecourseRepository.findByDatecourseArea(datecourse.getDatecourseArea(), pageable);
            } else if(!datecourse.getDatecourseCategory().equals("ALL")) {
                // 데이트 코스 분류만 입력된 경우
                return datecourseRepository.findByDatecourseCategory(datecourse.getDatecourseCategory(), pageable);
            } else if(datecourse.getSearchKeyword() != null && !datecourse.getSearchKeyword().equals("")) {
                // 검색어(데이트 코스명)만 입력된 경우
                return datecourseRepository.findByDatecourseNmContaining(datecourse.getSearchKeyword(), pageable);
            } else {
                // 지역="ALL" and 데이트 코스 분류="ALL" and 검색어(데이트 코스명)=null or ""
                System.out.println("test");
                return datecourseRepository.findAll(pageable);
            }
        } else {
            // 지역, 데이트 코스 분류, 검색어(데이트 코스명)가 모두 입력되지 않은 경우
            // 지역=null or "" and 데이트 코스 분류=null or "" and 검색어(데이트 코스명)=null or ""
            return datecourseRepository.findAll(pageable);
        }
    }
}
