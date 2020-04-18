package com.nhom4.vanphongphamonline.services;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nhom4.vanphongphamonline.models.FileData;
import com.nhom4.vanphongphamonline.repositories.FileDataRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FileStorageService {
	@Autowired
    private FileDataRepository fileDataRepository;


    // lưu local thì đổi File Data thành String
    public FileData storeFile(MultipartFile file) throws IOException {
        // Viết chuẩn tên tệp
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Kiểm tra file có chứa kí tự
        if(fileName.contains("..")) {
            return null;
        }

        // dùng cho local, lấy tên của file đã có copy file này và thay thế vào folder của file
//            Path targetLocation = this.fileStorageLocation.resolve(fileName);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        FileData fileData = new FileData(fileName, file.getContentType(), new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        return fileDataRepository.insert(fileData);
    }
    //load từ db
    public FileData getFile(String id) {
    	try {
    		return fileDataRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			 return null;
		}
    }
}
