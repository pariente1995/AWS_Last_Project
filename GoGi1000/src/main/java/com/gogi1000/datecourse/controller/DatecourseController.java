package com.gogi1000.datecourse.controller;

import com.gogi1000.datecourse.dto.DatecourseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/datecourse")
public class DatecourseController {

    // 데이트 코스 등록 화면으로 이동
    @GetMapping("/insertDatecourse")
    public ModelAndView insertDatecourseView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("datecourse/insertDatecourse.html");

        return mv;
    }

    // 데이트 코스 등록
    @PostMapping("/insertDatecourse")
    public void insertDatecourse(DatecourseDTO datecourseDTO, MultipartFile[] uploadFiles, HttpServletResponse response, HttpServletRequest request) throws IOException {
//        Datecourse datecourse = Datecourse.builder()
//                .datecourseNm(datecourseDTO.getDatecourseNm())
//                .datecourseArea(datecourseDTO.getDatecourseArea())
//                .datecourseCategory(datecourseDTO.getDatecourseCategory())
//                .datecourseSummary(datecourseDTO.getDatecourseSummary())
//                .datecourseDesc(datecourseDTO.getDatecourseDesc())
//                .datecourseAddr(datecourseDTO.getDatecourseAddr())
//                .datecourseAddr(datecourseDTO.getDatecourseAddr())
//                .datecourseInoutYn(datecourseDTO.getDatecourseInoutYn())
//                .datecourseOfficialSite(datecourseDTO.getDatecourseOfficialSite())
//                .datecou
//
//
//                .boardContent(datecourseDTO.getBoardContent())
//                .boardWriter(datecourseDTO.getBoardWriter())
//                .boardRegdate(LocalDateTime.now())
//                .build();
//
//        // DB에 입력될 파일 정보 리스트
//        List<BoardFile> uploadFileList = new ArrayList<BoardFile>();
//
//        if(uploadFiles.length > 0) {
//            System.out.println(request.getSession().getServletContext().getRealPath("/"));
//            String attachPath = request.getSession().getServletContext().getRealPath("/")
//                    + "/upload/";
//
//            File directory = new File(attachPath);
//
//            // 디렉토리가 존재하지 않으면 폴더 생성
//            if(!directory.exists()) {
//                directory.mkdir();
//            }
//
//            // multipartFile 형식의 데이터를 DB 테이블에 맞는 구조로 변경
//            for(int i=0; i<uploadFiles.length; i++) {
//                MultipartFile file = uploadFiles[i];
//
//                // 빈칸이 아니고 널이 아닐 때만 uploadFileList에 담기
//                if(!file.getOriginalFilename().equals("") && file.getOriginalFilename() != null) {
//                    BoardFile boardFile = new BoardFile();
//
//                    boardFile = FileUtils.parseFileInfo(file, attachPath);
//
//                    uploadFileList.add(boardFile);
//                }
//            }
//        }
//        boardService.insertBoard(board, uploadFileList);

        // 데이트 코스 리스트 화면으로 이동
        //response.sendRedirect("/board/boardList");
    }

    // 데이트 코스 수정 화면으로 이동
    @GetMapping("/updateDatecourse")
    public ModelAndView updateDatecourseView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("datecourse/updateDatecourse.html");

        return mv;
    }
}