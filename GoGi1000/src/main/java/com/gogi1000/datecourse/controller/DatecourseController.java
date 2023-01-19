package com.gogi1000.datecourse.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gogi1000.datecourse.common.CamelHashMap;
import com.gogi1000.datecourse.common.FileUtils;
import com.gogi1000.datecourse.dto.*;
import com.gogi1000.datecourse.entity.*;
import com.gogi1000.datecourse.service.datecourse.DatecourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/datecourse")
public class DatecourseController {
    @Autowired
    private DatecourseService datecourseService;

    // 데이트 코스 등록 화면으로 이동_세혁
    @GetMapping("/insertDatecourse")
    public ModelAndView insertDatecourseView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/insertDatecourse.html");

        return mv;
    }

    // 데이트 코스 등록_세혁
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
        response.sendRedirect("/datecourse/getDatecourseList");
    }

    // 데이트 코스 상세 조회_세혁
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
                .datecourseModfDate(datecourse.getDatecourseModfDate().toString())
                .datecourseUseYn(datecourse.getDatecourseUseYn())
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
                    .datecourseHoursRgstDate(datecourseHoursList.get(i).getDatecourseHoursRgstDate().toString())
                    .datecourseHoursUseYn(datecourseHoursList.get(i).getDatecourseHoursUseYn())
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
                    .datecourseMenuType(datecourseMenuList.get(i).getDatecourseMenuType())
                    .datecourseMenuNm(datecourseMenuList.get(i).getDatecourseMenuNm())
                    .datecourseMenuPrice(datecourseMenuList.get(i).getDatecourseMenuPrice())
                    .datecourseMenuRgstDate(datecourseMenuList.get(i).getDatecourseMenuRgstDate().toString())
                    .datecourseMenuUseYn(datecourseMenuList.get(i).getDatecourseMenuUseYn())
                    .build();

            getDatecourseMenuList.add(returnDatecourseMenu);
        }

        // 데이트 코스 이미지 리스트 조회
        List<DatecourseImage> datecourseImageList = datecourseService.getDatecourseImageList(datecourseNo);

        // 화면으로 전달할 데이트 코스 이미지 리스트
        List<DatecourseImageDTO> getDatecourseImageList = new ArrayList<>();

        for(DatecourseImage datecourseImage : datecourseImageList) {
            DatecourseImageDTO datecourseImageDTO = DatecourseImageDTO.builder()
                    .imageGroup(datecourseImage.getImageGroup())
                    .referenceNo(datecourseImage.getReferenceNo())
                    .imageNo(datecourseImage.getImageNo())
                    .imageNm(datecourseImage.getImageNm())
                    .imageOriginNm(datecourseImage.getImageOriginNm())
                    .imagePath(datecourseImage.getImagePath())
                    .imageRgstDate(datecourseImage.getImageRgstDate().toString())
                    .build();

            getDatecourseImageList.add(datecourseImageDTO);
        }

        ModelAndView mv = new ModelAndView();

        mv.setViewName("admin/getDatecourse.html");
        mv.addObject("getDatecourse", getDatecourse);
        mv.addObject("getDatecourseHoursList", getDatecourseHoursList);
        mv.addObject("getDatecourseMenuList", getDatecourseMenuList);
        mv.addObject("getDatecourseImageList", getDatecourseImageList);

        return mv;
    }

    // 데이트 코스 리스트 화면(관리자) 조회_세혁
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

    // 데이트 코스 리스트 화면(관리자)에서 삭제 시, 데이트 코스 사용여부를 ('Y' -> 'N')으로 업데이트_세혁
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

    // 데이트 코스 수정_세혁
    @Transactional // 쿼리가 실행된 후 바로 트랜잭션을 호출
    @PostMapping("/updateDatecourse")
    public ResponseEntity<?> updateDatecourse(DatecourseDTO datecourseDTO,
                                              @RequestParam("originDatecourseHours") String originDatecourseHours,
                                              @RequestParam("originDatecourseMenu") String originDatecourseMenu,
                                              @RequestParam("datecourseHoursInfo") String datecourseHoursInfo,
                                              @RequestParam("datecourseMenuInfo") String datecourseMenuInfo,
                                              @RequestParam("originFiles") String originFiles,
                                              MultipartFile[] realUploadFiles,
                                              MultipartFile[] changedFiles,
                                              HttpServletRequest request) throws IOException {

        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<>();

        // 기존 영업시간 리스트 정보
        List<DatecourseHoursDTO> originDatecourseHoursList = new ObjectMapper().readValue(originDatecourseHours, new TypeReference<List<DatecourseHoursDTO>>() {});

        // 기존 메뉴 리스트 정보
        List<DatecourseMenuDTO> originDatecourseMenuList = new ObjectMapper().readValue(originDatecourseMenu, new TypeReference<List<DatecourseMenuDTO>>() {});

        // 새로 추가된 영업시간 리스트 정보
        List<String> datecourseHoursList = new ObjectMapper().readValue(datecourseHoursInfo, new TypeReference<List<String>>() {});

        // 새로 추가된 메뉴 리스트 정보
        List<DatecourseMenuDTO> datecourseMenuList = new ObjectMapper().readValue(datecourseMenuInfo, new TypeReference<List<DatecourseMenuDTO>>() {});

        // 기존 첨부된 이미지 파일 리스트 정보
        List<DatecourseImageDTO> originFileList = new ObjectMapper().readValue(originFiles, new TypeReference<List<DatecourseImageDTO>>() {});

        String attachPath = request.getSession().getServletContext().getRealPath("/")
                + "/upload/";

        File directory = new File(attachPath);

        if(!directory.exists()) {
            directory.mkdir();
        }

        // DB에서 수정, 삭제, 추가 될 영업시간 정보를 담는 리스트
        List<DatecourseHours> uDatecourseHoursList = new ArrayList<DatecourseHours>();

        // DB에서 수정, 삭제, 추가 될 메뉴 정보를 담는 리스트
        List<DatecourseMenu> uDatecourseMenuList = new ArrayList<DatecourseMenu>();

        // DB에서 수정, 삭제, 추가 될 이미지 파일 정보를 담는 리스트
        List<DatecourseImage> uImageList = new ArrayList<DatecourseImage>();

        try {
            Datecourse datecourse = Datecourse.builder()
                    .datecourseNo(datecourseDTO.getDatecourseNo())
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
                    .datecourseRgstDate(LocalDateTime.parse(datecourseDTO.getDatecourseRgstDate()))
                    .datecourseModfDate(LocalDateTime.now()) // 수정일자는 현재일자로 수정되도록
                    .datecourseUseYn(datecourseDTO.getDatecourseUseYn())
                    .build();


            /**********************************
                영업시간 정보 처리
            ***********************************/
            for(int i=0; i<originDatecourseHoursList.size(); i++) {
                // 수정되는 영업시간 정보 처리
                if(originDatecourseHoursList.get(i).getDatecourseHoursStatus().equals("U")) {
                    DatecourseHours datecourseHours = new DatecourseHours();

                    datecourseHours.setDatecourseNo(originDatecourseHoursList.get(i).getDatecourseNo());
                    datecourseHours.setDatecourseHoursNo(originDatecourseHoursList.get(i).getDatecourseHoursNo());
                    datecourseHours.setDatecourseHoursInfo(originDatecourseHoursList.get(i).getNewDatecourseHoursInfo());
                    datecourseHours.setDatecourseHoursRgstDate(
                            LocalDateTime.parse(originDatecourseHoursList.get(i).getDatecourseHoursRgstDate()));
                    datecourseHours.setDatecourseHoursModfDate(LocalDateTime.now());
                    datecourseHours.setDatecourseHoursUseYn(originDatecourseHoursList.get(i).getDatecourseHoursUseYn());
                    datecourseHours.setDatecourseHoursStatus("U");

                    uDatecourseHoursList.add(datecourseHours);

                // 삭제되는 영업시간 정보 처리
                } else if(originDatecourseHoursList.get(i).getDatecourseHoursStatus().equals("D")) {
                    DatecourseHours datecourseHours = new DatecourseHours();

                    datecourseHours.setDatecourseNo(originDatecourseHoursList.get(i).getDatecourseNo());
                    datecourseHours.setDatecourseHoursNo(originDatecourseHoursList.get(i).getDatecourseHoursNo());
                    datecourseHours.setDatecourseHoursStatus("D");

                    uDatecourseHoursList.add(datecourseHours);
                }
            }

            // 추가되는 영업시간 정보 처리
            if(datecourseHoursList.size() > 0) {
                for(int i=0; i<datecourseHoursList.size(); i++) {
                    if(datecourseHoursList.get(i) != null && !datecourseHoursList.get(i).equals("")) {
                        DatecourseHours datecourseHours = new DatecourseHours();

                        datecourseHours.setDatecourseNo(datecourse.getDatecourseNo());
                        datecourseHours.setDatecourseHoursInfo(datecourseHoursList.get(i));
                        datecourseHours.setDatecourseHoursRgstDate(LocalDateTime.now());
                        datecourseHours.setDatecourseHoursModfDate(LocalDateTime.now());
                        datecourseHours.setDatecourseHoursUseYn("Y");
                        datecourseHours.setDatecourseHoursStatus("I");

                        uDatecourseHoursList.add(datecourseHours);
                    }
                }
            }


            /**********************************
                메뉴 정보 처리
            ***********************************/
            for(int i=0; i<originDatecourseMenuList.size(); i++) {
                // 수정되는 메뉴 정보 처리
                if(originDatecourseMenuList.get(i).getDatecourseMenuStatus().equals("U")) {
                    DatecourseMenu datecourseMenu = new DatecourseMenu();

                    datecourseMenu.setDatecourseNo(originDatecourseMenuList.get(i).getDatecourseNo());
                    datecourseMenu.setDatecourseMenuNo(originDatecourseMenuList.get(i).getDatecourseMenuNo());
                    datecourseMenu.setDatecourseMenuType(originDatecourseMenuList.get(i).getDatecourseMenuType());
                    if(originDatecourseMenuList.get(i).getNewDatecourseMenuNm() != null &&
                            !originDatecourseMenuList.get(i).getNewDatecourseMenuNm().equals("")) {
                        datecourseMenu.setDatecourseMenuNm(originDatecourseMenuList.get(i).getNewDatecourseMenuNm());
                    } else {
                        datecourseMenu.setDatecourseMenuNm(originDatecourseMenuList.get(i).getDatecourseMenuNm());
                    }
                    if(originDatecourseMenuList.get(i).getNewDatecourseMenuPrice() != 0) {
                        datecourseMenu.setDatecourseMenuPrice(originDatecourseMenuList.get(i).getNewDatecourseMenuPrice());
                    } else {
                        datecourseMenu.setDatecourseMenuPrice(originDatecourseMenuList.get(i).getDatecourseMenuPrice());
                    }
                    datecourseMenu.setDatecourseMenuRgstDate(
                            LocalDateTime.parse(originDatecourseMenuList.get(i).getDatecourseMenuRgstDate()));
                    datecourseMenu.setDatecourseMenuModfDate(LocalDateTime.now());
                    datecourseMenu.setDatecourseMenuUseYn(originDatecourseMenuList.get(i).getDatecourseMenuUseYn());
                    datecourseMenu.setDatecourseMenuStatus("U");

                    uDatecourseMenuList.add(datecourseMenu);

                // 삭제되는 메뉴 정보 처리
                } else if(originDatecourseMenuList.get(i).getDatecourseMenuStatus().equals("D")) {
                    DatecourseMenu datecourseMenu = new DatecourseMenu();

                    datecourseMenu.setDatecourseNo(originDatecourseMenuList.get(i).getDatecourseNo());
                    datecourseMenu.setDatecourseMenuNo(originDatecourseMenuList.get(i).getDatecourseMenuNo());
                    datecourseMenu.setDatecourseMenuStatus("D");

                    uDatecourseMenuList.add(datecourseMenu);
                }
            }

            // 추가되는 메뉴 정보 처리
            if(datecourseMenuList.size() > 0) {
                for(int i=0; i<datecourseMenuList.size(); i++) {
                    DatecourseMenu datecourseMenu = new DatecourseMenu();

                    datecourseMenu.setDatecourseNo(datecourse.getDatecourseNo());
                    datecourseMenu.setDatecourseMenuType(datecourseMenuList.get(i).getDatecourseMenuType());
                    if(datecourseMenuList.get(i).getDatecourseMenuNm() != null && !datecourseMenuList.get(i).getDatecourseMenuNm().equals("")) {
                        datecourseMenu.setDatecourseMenuNm(datecourseMenuList.get(i).getDatecourseMenuNm());
                    }

                    if(datecourseMenuList.get(i).getDatecourseMenuPrice() != 0) {
                        datecourseMenu.setDatecourseMenuPrice(datecourseMenuList.get(i).getDatecourseMenuPrice());
                    }
                    datecourseMenu.setDatecourseMenuRgstDate(LocalDateTime.now());
                    datecourseMenu.setDatecourseMenuModfDate(LocalDateTime.now());
                    datecourseMenu.setDatecourseMenuUseYn("Y");
                    datecourseMenu.setDatecourseMenuStatus("I");

                    uDatecourseMenuList.add(datecourseMenu);
                }
            }


            /**********************************
                파일 처리
            ***********************************/
            for(int i=0; i<originFileList.size(); i++) {
                // 수정되는 파일 처리
                if(originFileList.get(i).getDatecourseImageStatus().equals("U")) {
                    for(int j=0; j<changedFiles.length; j++) {
                        if(originFileList.get(i).getNewImageNm().equals(changedFiles[j].getOriginalFilename())) {
                            DatecourseImage datecourseImage = new DatecourseImage();

                            MultipartFile file = changedFiles[j];

                            datecourseImage = FileUtils.parseFileInfo(file, attachPath);
                            datecourseImage.setImageGroup(originFileList.get(i).getImageGroup());
                            datecourseImage.setReferenceNo(originFileList.get(i).getReferenceNo());
                            datecourseImage.setImageNo(originFileList.get(i).getImageNo());
                            datecourseImage.setImageRgstDate(LocalDateTime.parse(originFileList.get(i).getImageRgstDate()));
                            datecourseImage.setImageModfDate(LocalDateTime.now());
                            datecourseImage.setDatecourseImageStatus("U");

                            uImageList.add(datecourseImage);
                        }
                    }
                    // 삭제되는 파일 처리
                } else if(originFileList.get(i).getDatecourseImageStatus().equals("D")) {
                    DatecourseImage datecourseImage = new DatecourseImage();

                    datecourseImage.setImageGroup(originFileList.get(i).getImageGroup());
                    datecourseImage.setReferenceNo(originFileList.get(i).getReferenceNo());
                    datecourseImage.setImageNo(originFileList.get(i).getImageNo());
                    datecourseImage.setDatecourseImageStatus("D");

                    // 실제 파일 삭제
                    File dFile = new File(attachPath + originFileList.get(i).getImageNm());
                    dFile.delete();

                    uImageList.add(datecourseImage);
                }
            }

            // 추가되는 파일 처리
            if(realUploadFiles.length > 0) {
                for(int i=0; i<realUploadFiles.length; i++) {
                    MultipartFile file = realUploadFiles[i];

                    if(file.getOriginalFilename() != null &&
                            !file.getOriginalFilename().equals("")) {
                        DatecourseImage datecourseImage = new DatecourseImage();

                        datecourseImage = FileUtils.parseFileInfo(file, attachPath);

                        datecourseImage.setImageGroup("E0001");
                        datecourseImage.setReferenceNo(datecourse.getDatecourseNo());
                        datecourseImage.setImageRgstDate(LocalDateTime.now());
                        datecourseImage.setImageModfDate(LocalDateTime.now());
                        datecourseImage.setDatecourseImageStatus("I");

                        uImageList.add(datecourseImage);
                    }
                }
            }

            datecourseService.updateDatecourse(datecourse, uDatecourseHoursList, uDatecourseMenuList, uImageList);

            DatecourseDTO returnDatecourse = DatecourseDTO.builder()
                    .datecourseNo(datecourse.getDatecourseNo())
                    .build();

            Map<String, Object> returnMap = new HashMap<String, Object>();

            returnMap.put("getDatecourse", returnDatecourse);

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);
        } catch(Exception e) {
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    // 카테고리 선택에 따른 데이트 코스 조회_세혁
    @GetMapping("getPageCateDatecourseList")
    public Map<String, Object> getPageCateDatecourseList(DatecourseDTO datecourseDTO,
                                                         @PageableDefault(page=0, size=12) Pageable pageable,
                                                         @AuthenticationPrincipal CustomUserDetails customUser) {
        Map<String,Object> returnMap =new HashMap<String, Object>();

        Page<CamelHashMap> getPageCateDatecourseList = datecourseService.getPageCateDatecourseList(datecourseDTO, pageable, customUser);

        returnMap.put("getCateDatecourseList", getPageCateDatecourseList);

        return returnMap;
    }

    /*
        데이트 코스 삭제_세혁
        - 데이트 코스, 데이트 코스 영업시간, 데이트 코스 메뉴, 데이트 코스 이미지 삭제
        - 데이트 코스 좋아요, MY 데이트 코스, 데이트 코스 리뷰 삭제
    */
    @Transactional // 쿼리가 실행된 후 바로 트랜잭션을 호출
    @PostMapping("deleteDatecourse")
    public void deleteDatecourse(@RequestParam("datecourseNo") int datecourseNo) {
        datecourseService.deleteDatecourse(datecourseNo);
    }
}