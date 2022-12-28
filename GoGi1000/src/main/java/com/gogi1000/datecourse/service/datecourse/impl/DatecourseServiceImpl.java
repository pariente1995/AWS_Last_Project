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
}
