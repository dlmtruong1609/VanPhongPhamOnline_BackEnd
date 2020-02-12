package com.nhom4.vanphongphamonline.services;

import java.io.IOException;
import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nhom4.vanphongphamonline.model.HinhAnh;
import com.nhom4.vanphongphamonline.repository.HinhAnhRepository;

@Service
public class HinhAnhService {

	@Autowired
	HinhAnhRepository hinhAnhRepository;
    public HinhAnh addImg(String title, MultipartFile file) throws IOException { 
        HinhAnh hinhAnh = new HinhAnh(title); 
        hinhAnh.setImg(
          new Binary(BsonBinarySubType.BINARY, file.getBytes())); 
        hinhAnh = hinhAnhRepository.insert(hinhAnh); return hinhAnh; 
    }
 
    public Optional<HinhAnh> getImg(String id) { 
        return hinhAnhRepository.findById(id); 
    }
}
