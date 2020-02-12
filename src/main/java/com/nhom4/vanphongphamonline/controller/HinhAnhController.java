package com.nhom4.vanphongphamonline.controller;

import java.io.IOException;
import java.util.Base64;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nhom4.vanphongphamonline.model.HinhAnh;
import com.nhom4.vanphongphamonline.services.HinhAnhService;

@Controller
public class HinhAnhController {
	@Autowired
	HinhAnhService hinhAnhService;
	
	@ResponseBody
	@PostMapping(value = "/api/img/them")
	public ResponseEntity<HinhAnh> addImage(@RequestParam String title, @RequestParam MultipartFile file, Model model) throws IOException {
		return new ResponseEntity<HinhAnh>(hinhAnhService.addImg(title, file), HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "/api/img/hinhanh")
	public ResponseEntity<Binary> getImage(@RequestParam String id, Model model) {
		HinhAnh hinhAnh = hinhAnhService.getImg(id).get();
		return new ResponseEntity<Binary>(hinhAnh.getImg(), HttpStatus.OK);
	}
}
