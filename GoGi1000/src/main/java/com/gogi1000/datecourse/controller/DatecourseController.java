package com.gogi1000.datecourse.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gogi1000.datecourse.common.FileUtils;
import com.gogi1000.datecourse.dto.*;
import com.gogi1000.datecourse.entity.Datecourse;
import com.gogi1000.datecourse.entity.DatecourseHours;
import com.gogi1000.datecourse.entity.DatecourseImage;
import com.gogi1000.datecourse.entity.DatecourseMenu;
import com.gogi1000.datecourse.service.datecourse.DatecourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
        mv.setViewName("admin/insertDatecourse.html");

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

        // 데이트 코스 리스트 화면(관리자)으로 이동
        //response.sendRedirect("/admin/getDatecourseList");
    }

    // 데이트 코스 상세 조회
    @GetMapping("/getDatecourse/{datecourseNo}")
    public ModelAndView getDatecourse(@PathVariable int datecourseNo) {
        // 데이트 코스 조회
        Datecourse datecourse = datecourseService.getDatecourse(datecourseNo);

        DatecourseDTO getDatecourse = DatecourseDTO.builder()
                .datecourseRgstDate(datecourse.getDatecourseRgstDate().toString())
                .datecourseNo(datecourse.getDatecourseNo())
                .datecourseNm(datecourse.getDatecourseNm())
                .datecourseArea(datecourse.getDatecourseArea())
                .datecourseCategory(datecourse.getDatecourseCategory())
                .datecourseSummary(datecourse.getDatecourseSummary())
                .datecourseDesc(datecourse.getDatecourseDesc())
                .datecourseAddr(datecourse.getDatecourseAddr())
                .datecourseInoutYn(datecourse.getDatecourseInoutYn())
                .datecourseFoodType(datecourse.getDatecourseFoodType())
                .datecourseTel(datecourse.getDatecourseTel())
                .datecourseOfficialSite(datecourse.getDatecourseOfficialSite())
                .datecourseParkingYn(datecourse.getDatecourseParkingYn())
                .datecoursePetYn(datecourse.getDatecoursePetYn())
                .build();
        
        // 데이트 코스 영업시간 조회
        List<DatecourseHours> datecourseHoursList = datecourseService.getDatecourseHoursList(datecourseNo);

        // 화면으로 전달할 데이트 코스 영업시간 리스트
        List<DatecourseHoursDTO> getDatecourseHoursList = new ArrayList<>();

        for(int i=0; i<datecourseHoursList.size(); i++) {
            DatecourseHoursDTO returnDatecourseHours = DatecourseHoursDTO.builder()
                    .datecourseNo(datecourseHoursList.get(i).getDatecourseNo())
                    .datecourseHoursNo(datecourseHoursList.get(i).getDatecourseHoursNo())
                    .datecourseHoursInfo(datecourseHoursList.get(i).getDatecourseHoursInfo())
                    .build();

            getDatecourseHoursList.add(returnDatecourseHours);
        }

        // 데이트 코스 메뉴 조회
        List<DatecourseMenu> datecourseMenuList = datecourseService.getDatecourseMenuList(datecourseNo);

        // 화면으로 전달할 데이트 코스 메뉴 리스트
        List<DatecourseMenuDTO> getDatecourseMenuList = new ArrayList<>();

        for(int i=0; i<datecourseMenuList.size(); i++) {
            DatecourseMenuDTO returnDatecourseMenu = DatecourseMenuDTO.builder()
                    .datecourseNo(datecourseMenuList.get(i).getDatecourseNo())
                    .datecourseMenuNo(datecourseMenuList.get(i).getDatecourseMenuNo())
                    .datecourseMenuNm(datecourseMenuList.get(i).getDatecourseMenuNm())
                    .datecourseMenuPrice(datecourseMenuList.get(i).getDatecourseMenuPrice())
                    .build();

            getDatecourseMenuList.add(returnDatecourseMenu);
        }

        // 데이트 코스 이미지 리스트 조회
        List<DatecourseImage> datecourseImageList = datecourseService.getDatecourseImageList(datecourseNo);

        // 화면으로 전달할 데이트 코스 이미지 리스트
        List<DatecourseImageDTO> getDatecourseImageList = new ArrayList<>();

        for(DatecourseImage datecourseImage : datecourseImageList) {
            DatecourseImageDTO datecourseImageDTO = DatecourseImageDTO.builder()
                    .referenceNo(datecourseImage.getReferenceNo())
                    .imageNo(datecourseImage.getImageNo())
                    .imageNm(datecourseImage.getImageNm())
                    .imagePath(datecourseImage.getImagePath())
                    .build();

            getDatecourseImageList.add(datecourseImageDTO);
        }

        System.out.println(getDatecourse);
        System.out.println(getDatecourseHoursList);
        System.out.println(getDatecourseMenuList);
        System.out.println(getDatecourseImageList);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/getDatecourse.html");
        mv.addObject("getDatecourse", getDatecourse);
        mv.addObject("getDatecourseHoursList", getDatecourseHoursList);
        mv.addObject("getDatecourseMenuList", getDatecourseMenuList);
        mv.addObject("getDatecourseImageList", getDatecourseImageList);

        return mv;
    }

    // 데이트 코스 리스트 화면(관리자) 조회
    @GetMapping("/getDatecourseList")
    public ModelAndView getPageDatecourseList(DatecourseDTO datecourseDTO, @PageableDefault(page=0, size=15) Pageable pageable) {
        Datecourse datecourse = Datecourse.builder()
                .datecourseArea(datecourseDTO.getDatecourseArea())
                .datecourseCategory(datecourseDTO.getDatecourseCategory())
                .searchKeyword(datecourseDTO.getSearchKeyword())
                .build();

        Page<Datecourse> pageDatecourseList = datecourseService.getPageDatecourseList(datecourse, pageable);

        Page<DatecourseDTO> pageDatecourseDTOList = pageDatecourseList.map(pageDatecourse ->
                DatecourseDTO.builder()
                        .datecourseNo(pageDatecourse.getDatecourseNo())
                        .datecourseNm(pageDatecourse.getDatecourseNm())
                        .datecourseModfDate(pageDatecourse.getDatecourseModfDate().toString())
                        .datecourseUseYn(pageDatecourse.getDatecourseUseYn())
                        .build()
        );

        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/getDatecourseList.html");
        mv.addObject("getDatecourseList", pageDatecourseDTOList);

        if(datecourseDTO.getDatecourseArea() != null && !datecourseDTO.getDatecourseArea().equals("")) {
            mv.addObject("datecourseArea", datecourseDTO.getDatecourseArea());
        }

        if(datecourseDTO.getDatecourseCategory() != null && !datecourseDTO.getDatecourseCategory().equals("")) {
            mv.addObject("datecourseCategory", datecourseDTO.getDatecourseCategory());
        }

        if(datecourseDTO.getSearchKeyword() != null && !datecourseDTO.getSearchKeyword().equals("")) {
            mv.addObject("datecourseSearchKeyword", datecourseDTO.getSearchKeyword());
        }

        return mv;
    }

    // 데이트 코스 리스트 화면(관리자)에서 삭제 시, 데이트 코스 사용여부를 ('Y' -> 'N')으로 업데이트
    @PostMapping("/updateDatecourseList")
    public ResponseEntity<?> updateDatecourseList(
            DatecourseDTO datecourseDTO,
            @RequestParam("updateRows") String updateRows,
            @PageableDefault(page=0, size=15) Pageable pageable) throws IOException {
        ResponseDTO<DatecourseDTO> response = new ResponseDTO<>();

        List<Integer> updateRowsList = new ObjectMapper().readValue(updateRows, new TypeReference<List<Integer>>() {});

        try {
            // 업데이트
            datecourseService.updateDatecourseList(updateRowsList);

            Datecourse datecourse = Datecourse.builder()
                    .datecourseArea(datecourseDTO.getDatecourseArea())
                    .datecourseCategory(datecourseDTO.getDatecourseCategory())
                    .searchKeyword(datecourseDTO.getSearchKeyword())
                    .build();

            // 변경된 내용으로 조회
            Page<Datecourse> pageDatecourseList = datecourseService.getPageDatecourseList(datecourse, pageable);

            Page<DatecourseDTO> pageDatecourseDTOList = pageDatecourseList.map(pageDatecourse ->
                    DatecourseDTO.builder()
                            .datecourseNo(pageDatecourse.getDatecourseNo())
                            .datecourseUseYn(pageDatecourse.getDatecourseUseYn())
                            .build()
            );

            response.setPageItems(pageDatecourseDTOList);
            return ResponseEntity.ok().body(response);
        } catch(Exception e) {
            response.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}