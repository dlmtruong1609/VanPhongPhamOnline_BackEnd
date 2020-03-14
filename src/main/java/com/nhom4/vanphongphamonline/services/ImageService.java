package com.nhom4.vanphongphamonline.services;

import java.io.IOException;
import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nhom4.vanphongphamonline.model.Image;
import com.nhom4.vanphongphamonline.repository.ImageRepository;

@Service
public class ImageService {

	@Autowired
	ImageRepository imageRepository;
    public Image addImg(String title, MultipartFile file) throws IOException { 
        Image image = new Image(title); 
        image.setImg(
          new Binary(BsonBinarySubType.BINARY, file.getBytes())); 
        image = imageRepository.insert(image); return image; 
    }
 
    public Optional<Image> getImg(String id) { 
        return imageRepository.findById(id); 
    }
}
