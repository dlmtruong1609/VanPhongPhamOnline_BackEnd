package com.nhom4.vanphongphamonline.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nhom4.vanphongphamonline.model.FileData;
import com.nhom4.vanphongphamonline.services.FileStorageService;
import com.nhom4.vanphongphamonline.services.ServiceStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Controller
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
  @PostMapping("/api/v1/file/uploadFile")
  public ResponseEntity<ServiceStatus> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
      FileData fileData = fileStorageService.storeFile(file);

      String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
              .path("/api/v1/file/")
              .path(fileData.getId())
              .toUriString();

      return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Upload thành công", fileDownloadUri), HttpStatus.OK);
  }


    @GetMapping("/api/v1/file/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id, HttpServletRequest request) {
    	FileData fileData = fileStorageService.getFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileData.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileData.getName() + "\"")
                .body(new ByteArrayResource(fileData.getFile().getData()));
    }
    
    
    
    // lưu xuống local
//  @PostMapping("/api/v1/file/uploadFile")
//  public ResponseEntity<ServiceStatus> uploadFile(@RequestParam("file") MultipartFile file) {
//      String fileName = fileStorageService.storeFile(file);
//
//      String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//              .path("/api/v1/file/")
//              .path(fileName)
//              .toUriString();
//
//      return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Upload thành công", fileDownloadUri), HttpStatus.OK);
//  }

}