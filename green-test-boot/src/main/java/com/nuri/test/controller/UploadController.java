package com.nuri.test.controller;

import com.nuri.test.beans.vo.AttachFileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/upload/*")
public class UploadController {
    @GetMapping("options")
    public void options(){
        log.info("upload options");
    }

    @GetMapping("uploadForm")
    public void uploadForm(){
        log.info("upload form");
    }

    @PostMapping("uploadFormAction")
    public String uploadFormAction(MultipartFile[] uploadFiles, Model model){
        List<AttachFileVO> fileList = new ArrayList<>();//2021.12.07

        for(MultipartFile multipartFile : uploadFiles){

            String uploadFolder = "C:/upload";

            log.info("-------------------------");
            log.info("Upload File Name : " + multipartFile.getOriginalFilename());
            log.info("Upload File Size : " + multipartFile.getSize());

            File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());

            /*2021.12.07*/
            AttachFileVO attachFileVO = new AttachFileVO();
            String uploadFileName = multipartFile.getOriginalFilename();
            log.info("file name : " + uploadFileName);
            attachFileVO.setFileName(uploadFileName);
            attachFileVO.setUploadPath(uploadFolder);
            fileList.add(attachFileVO);
            model.addAttribute("fileList", fileList);

            try {
                multipartFile.transferTo(saveFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "upload/uploadForm";
    }

    @GetMapping("uploadAjax")
    public void uploadAjax(){
        log.info("upload ajax");
    }

//    * 문제점 및 해결방안
//    1. 동일한 이름으로 파일이 업로드 되었을 때 기존 파일이 사라지는 문제 // 1. 년,월,일 로 2. UUID
//    2. 이미지 파일과 일반 파일을 구분해서 다운로드 혹은 페이지에서 조회하도록 처리해야 하는 문제
//    3. 첨부파일 공격에 대비하기 위한 업로드 파일의 확장자 제한
//    5. 등록을 하지 않고 업로드만 되어있는 파일을 DB와 비교하여 삭제해야 하는 문제

    @PostMapping(value = "uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<AttachFileVO> uploadAjaxAction(MultipartFile[] uploadFiles){
        log.info("upload ajax action...........");
        List<AttachFileVO> fileList = new ArrayList<>();

        String uploadFolder = "C:/upload";
        String uploadFolderPath = getFolder();

//        년/월/일 폴더 생성
        File uploadPath = new File(uploadFolder, uploadFolderPath);
        if(!uploadPath.exists()) {uploadPath.mkdirs();}
        log.info("upload path : " + uploadPath);

        for(MultipartFile multipartFile : uploadFiles){
            log.info("-------------------------");
            log.info("Upload File Name : " + multipartFile.getOriginalFilename());
            log.info("Upload File Size : " + multipartFile.getSize());

            AttachFileVO attachFileVO = new AttachFileVO();

            String uploadFileName = multipartFile.getOriginalFilename();

            log.info("file name : " + uploadFileName);

            attachFileVO.setFileName(uploadFileName);

            try {
                File saveFile = new File(uploadPath,uploadFileName);
                multipartFile.transferTo(saveFile);
                attachFileVO.setUploadPath(uploadFolderPath);


                fileList.add(attachFileVO);
            } catch (IOException e) {
                log.error(e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e){
                log.info("파일의 용량이 큽니다.");
            }
        }
        return fileList;
    }

    private String getFolder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String now = sdf.format(date);

//        - 대신 각 디렉토리의 경로를 구분할 수 있도록 하기 위해서
//        replace()를 사용한다.
        return now.replace("-", "/");
    }



    @GetMapping("display")
    @ResponseBody
    public ResponseEntity<byte[]> getFile(String fileName){
        File file = new File("C:/upload/" + fileName);
        log.info("file : " + file);
        HttpHeaders header = new HttpHeaders();
        ResponseEntity<byte[]> result = null;
        try {
            //png 파일이면 image/png타입, jpeg파일이면 image/jpeg 타입으로 설정
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping(value = "download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE) //MediaType.APPLICATION_JSON_VALUE였는데 byte를 JSON_VALUE로 보낼 수 없음. 그래서 octet 스트림으로 보냄.
    @ResponseBody //Resouce로 응답을 해줄 예정(브라우저가 그 byte를 출력할 수 있음)
    public ResponseEntity<Resource> downloadFile(String fileName){ //core.io.Resouce
        log.info("download file : " + fileName);
        Resource resource = new FileSystemResource("C:/upload/" + fileName); //여기다가 경로를 써줌. 년,월,일 포함
        log.info("resource : " + resource);
        String resourceName = resource.getFilename(); //해당 경로에 있는 파일이름 가져옴. 그리고 header에다가 넣을 예정
        //header에 담아서 전달을 해주면 브라우저가 인식을 합니다.
        HttpHeaders headers = new HttpHeaders();

        try {
            //Disposition : 어떤 이름으로 다운로드 창에 보이게 할건지 정함.
            //headerValue가 "attachment; filename="이면, 브라우저에서 '아 얘를 출력해야되는 거구나' 그래서 다운로드가 됨.
            headers.add("Content-Disposition", "attachment; filename=" + new String(resourceName.getBytes("UTF-8"), "ISO-8859-1"));

            //try-catch 하는 이유: 혹시라도 잘못된 인코딩을 하는 경우 처리를 해줘야 한다.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);


        //순서
        //1. 사용자에게 전달받은 fileName을
        //2. C:/upload/ 절대경로에 붙여서 resource 타입으로 변경해주고
        //3. C:/upload/파일이름  을 byte로 바꾸되 utf-8로 바꾸고
        //4. OCTET_STREAM_VALUE 에 맞춰 인코딩 방식을 ISO-8859-1 방식으로 변경함. 그걸 header에 담음
        //5. 158번째줄, 해당 resouce 경로에 있는 파일을 headers의 타입으로 전달
    }

    /*@PostMapping("deleteFile")
    @ResponseBody
    public ResponseEntity<String> deleteFile(String fileName, String type){
        log.info("deleteFile : " + fileName);
        try {
            fileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File("C:/upload/" + fileName);
            file.delete();
            if(type.equals("image")){
                //원본 삭제
                new File(file.getPath().replace("s_", "")).delete();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }*/

}

















