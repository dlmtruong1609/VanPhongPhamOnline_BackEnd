package com.nhom4.vanphongphamonline.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nhom4.vanphongphamonline.model.Image;
import com.nhom4.vanphongphamonline.services.ImageService;
import com.nhom4.vanphongphamonline.services.ServiceStatus;

@Controller
public class ImageController {
	@Autowired
	ImageService hinhAnhService;
	
	@ResponseBody
	@PostMapping(value = "/api/v1/img/upload")
	public ResponseEntity<ServiceStatus> addImage(@RequestParam String title, @RequestParam MultipartFile file, Model model) throws IOException {
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "upload thành công", hinhAnhService.addImg(title, file)), HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "/api/v1/img/data")
	public ResponseEntity<ServiceStatus> getImage(@RequestParam String id, Model model) {
		Image image = hinhAnhService.getImg(id).get();
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, "Lấy hình ảnh", image.getImg()), HttpStatus.OK);
//		trả về hình ảnh
//		Encoder encoder = Base64.getEncoder();
//		return encoder.encodeToString(hinhAnh.getImg().getData());
	}
}