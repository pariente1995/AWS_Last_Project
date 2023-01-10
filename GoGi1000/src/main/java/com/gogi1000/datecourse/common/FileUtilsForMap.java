package com.gogi1000.datecourse.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileUtilsForMap {
    // Map<String, String> => 파일 업로드 기능이 여러군데에서 사용될 때 범용성을 높이기 위해 Map을 사용한다.
    // Map을 사용할 경우 매퍼까지 Map으로 보내준다.
    public static CamelHashMap parseFileInfo(MultipartFile file, String attachPath) throws IOException {
    	CamelHashMap datecourseImageMap = new CamelHashMap();
    	
        // 업로드된 원래의 이미지 파일명
        String imageOriginNm = file.getOriginalFilename();

        SimpleDateFormat formmater = new SimpleDateFormat("yyyyMMddHHmmss");
        Date nowDate = new Date();
        String nowDateStr = formmater.format(nowDate);
        UUID uuid = UUID.randomUUID();

        String imageNm = nowDateStr + "_" + uuid.toString() + "_" + imageOriginNm;
        String imagePath = attachPath;

        /* 이미지 파일만 업로드 되기에 기존 실습 시 했던 아래의 코드는 생략하고, 확장자만 설정 */
        /*
        File checkFile = new File(imageOriginNm);
        String type = Files.probeContentType(checkFile.toPath());

        if(type != null) {
            if(type.startsWith("image")) {
                boardFile.setBoardFileCate("img");
            } else {
                boardFile.setBoardFileCate("etc");
            }
        } else {
            boardFile.setBoardFileCate("etc");
        }
        */

        // 확장자 셋팅
        // 파일 이름에서 마지막에 있는 .을 찾고 그 뒤의 문자열(확장자)을 가져온다.
        String imageExt = imageOriginNm.substring(imageOriginNm.lastIndexOf(".") + 1);
        
        
        datecourseImageMap.put("image_Nm", imageNm);
        System.out.println("fileutils" + datecourseImageMap);
        datecourseImageMap.put("image_Origin_Nm", imageOriginNm);
        datecourseImageMap.put("image_Ext", imageExt);
        datecourseImageMap.put("image_Path", imagePath);
        System.out.println("fileutils" + datecourseImageMap);
        // 실제 파일 업로드
        File uploadFile = new File(attachPath + imageNm);
        // 매개변수는 업로드될 폴더와 파일명을 파일객체 형태로 넣어준다.
        // 파일업로드 시, IOException 처리
        file.transferTo(uploadFile);

        return datecourseImageMap;
    }
}
