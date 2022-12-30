package com.gogi1000.datecourse.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gogi1000.datecourse.common.FileUtils;
import com.gogi1000.datecourse.dto.DatecourseDTO;
import com.gogi1000.datecourse.dto.DatecourseMenuDTO;
import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.entity.DatecourseHours;
import com.gogi1000.datecourse.entity.DatecourseImage;
import com.gogi1000.datecourse.entity.DatecourseMenu;
import com.gogi1000.datecourse.service.datecourse.DatecourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/datecourse")
public class DatecourseController {
    @Autowired
    private DatecourseService datecourseService;


    // 데이트 코스 등록 화면으로 이동
    @GetMapping("/insertDatecourse")
    public ModelAndView insertDatecourseView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("datecourse/insertDatecourse.html");

        return mv;
    }

    // 데이트 코스 등록
    @PostMapping("/insertDatecourse")
    // realUploadFiles(매개변수)는 input의 name과 동일해야 한다.
    public void insertDatecourse(DatecourseDTO datecourseDTO,
                                 @RequestParam("datecourseHoursInfo") String datecourseHoursInfo,
                                 @RequestParam("datecourseMenuInfo") String datecourseMenuInfo,
                                 MultipartFile[] realUploadFiles,
                                 HttpServletResponse response, HttpServletRequest request) throws IOException {

        // 데이트 코스
        Datecourse datecourse = Datecourse.builder()
                                          .datecourseNm(datecourseDTO.getDatecourseNm())
                                          .datecourseArea(datecourseDTO.getDatecourseArea())
                                          .datecourseCategory(datecourseDTO.getDatecourseCategory())
                                          .datecourseSummary(datecourseDTO.getDatecourseSummary())
                                          .datecourseDesc(datecourseDTO.getDatecourseDesc())
                                          .datecourseAddr(datecourseDTO.getDatecourseAddr())
                                          .datecourseInoutYn(datecourseDTO.getDatecourseInoutYn())
                                          .datecourseFoodType(datecourseDTO.getDatecourseFoodType())
                                          .datecourseTel(datecourseDTO.getDatecourseTel())
                                          .datecourseOfficialSite(datecourseDTO.getDatecourseOfficialSite())
                                          .datecourseParkingYn(datecourseDTO.getDatecourseParkingYn())
                                          .datecoursePetYn(datecourseDTO.getDatecoursePetYn())
                                          .datecourseRgstDate(LocalDateTime.now())
                                          .datecourseModfDate(LocalDateTime.now())
                                          .datecourseUseYn("Y")
                                          .build();

        // 데이트 코스 영업시간
        // 화면에서 받아온 영업시간 데이터
        List<String> datecourseHoursList = new ObjectMapper().readValue(datecourseHoursInfo, new TypeReference<List<String>>() {});

        // DB에 입력될 영업시간 정보 리스트
        List<DatecourseHours> iDatecourseHoursList = new ArrayList<DatecourseHours>();

        for(int i=0; i<datecourseHoursList.size(); i++) {
            DatecourseHours datecourseHours = DatecourseHours.builder()
                                                             .datecourseHoursInfo(datecourseHoursList.get(i))
                                                             .datecourseHoursRgstDate(LocalDateTime.now())
                                                             .datecourseHoursModfDate(LocalDateTime.now())
                                                             .datecourseHoursUseYn("Y")
                                                             .build();

            iDatecourseHoursList.add(datecourseHours);
        }

        // 데이트 코스 메뉴
        // 화면에서 받아온 메뉴 데이터
        List<DatecourseMenuDTO> datecourseMenuDTOList = new ObjectMapper().readValue(datecourseMenuInfo, new TypeReference<List<DatecourseMenuDTO>>() {});

        // DB에 입력될 메뉴 정보 리스트
        List<DatecourseMenu> iDatecourseMenuList = new ArrayList<DatecourseMenu>();

        for(int i=0; i<datecourseMenuDTOList.size(); i++) {
            DatecourseMenu datecourseMenu = DatecourseMenu.builder()
                                                          .datecourseMenuType(datecourseMenuDTOList.get(i).getDatecourseMenuType())
                                                          .datecourseMenuNm(datecourseMenuDTOList.get(i).getDatecourseMenuNm())
                                                          .datecourseMenuPrice(datecourseMenuDTOList.get(i).getDatecourseMenuPrice())
                                                          .datecourseMenuRgstDate(LocalDateTime.now())
                                                          .datecourseMenuModfDate(LocalDateTime.now())
                                                          .datecourseMenuUseYn("Y")
                                                          .build();

            iDatecourseMenuList.add(datecourseMenu);
        }

        // DB에 입력될 이미지파일 정보 리스트
        List<DatecourseImage> uploadImageList = new ArrayList<DatecourseImage>();

        if(realUploadFiles.length > 0) {
            System.out.println(request.getSession().getServletContext().getRealPath("/"));
            String attachPath = request.getSession().getServletContext().getRealPath("/")
                    + "/upload/";

            File directory = new File(attachPath);

            // 디렉토리가 존재하지 않으면 폴더 생성
            if(!directory.exists()) {
                directory.mkdir();
            }

            // multipartFile 형식의 데이터를 DB 테이블에 맞는 구조로 변경
            for(int i=0; i<realUploadFiles.length; i++) {
                MultipartFile file = realUploadFiles[i];

                // 빈칸이 아니고 널이 아닐 때만 uploadFileList에 담기
                if(!file.getOriginalFilename().equals("") && file.getOriginalFilename() != null) {
                    DatecourseImage datecourseImage = new DatecourseImage();

                    datecourseImage = FileUtils.parseFileInfo(file, attachPath);

                    uploadImageList.add(datecourseImage);
                }
            }
        }
        datecourseService.insertDatecourse(datecourse, iDatecourseHoursList, iDatecourseMenuList, uploadImageList);

        // 데이트 코스 리스트 화면으로 이동
        //response.sendRedirect("/datecourse/boardList");
    }

    // 데이트 코스 수정 화면으로 이동
    @GetMapping("/updateDatecourse")
    public ModelAndView updateDatecourseView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("datecourse/updateDatecourse.html");

        return mv;
    }
}