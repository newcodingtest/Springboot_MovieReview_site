package org.yoon.moviereview.controller;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.yoon.moviereview.dto.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${org.yoon.upload.path}")
    private String uploadPath;
    
    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>>uploadFile(MultipartFile[] uploadFiles){

        List<UploadResultDTO> resultDTOList = new ArrayList<>();
        for (MultipartFile uploadFile : uploadFiles){

            //이미지 파일만 업로드 가능
            if(uploadFile.getContentType().startsWith("image") == false){
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            //실제 파일 이름  IE / EDGE는 전체 경로가 들어옴으로 문자열 자름 처리를 해주자
            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);

            log.info("업로드 fileName: "+fileName);

            String folderPath = makeFolder();

            String uuid = UUID.randomUUID().toString();
            //저장할 파일 이름 중간에 _을 이용하여 구분
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid+"_" + fileName;

            Path savePath = Paths.get(saveName);

            try {
                //원본 파일 저장
                uploadFile.transferTo(savePath);

                //썸네일
                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid+"_" + fileName;
                //썸네일 파일 이름은 중간에 s로 시작하도록
                File thumbnailFile = new File(thumbnailSaveName);
                //썸네일 생성
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile,100,100 );
                resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        log.info("최종 파일 주소: "+resultDTOList);
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }

    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/",File.separator);

        //폴더 만들기
        File uploadPathFolder = new File(uploadPath, folderPath);

        if(uploadPathFolder.exists() == false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName, String size){

        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("fileName: "+srcFileName);

            File file = new File(uploadPath + File.separator + srcFileName);

            if(size != null && size.equals("1")){
                file = new File(file.getParent(), file.getName().substring(2));
            }
            log.info("file: " + file);
            HttpHeaders header = new HttpHeaders();
            
            //MIME 타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
            
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){
        String srcFileName = null;
        try {
            srcFileName = URLDecoder.decode(fileName,"UTF-8");
            File file = new File(uploadPath+File.separator+srcFileName);
            boolean result = file.delete();

            File thumbnail = new File(file.getParent(), "s_" + file.getName());

            result = thumbnail.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
