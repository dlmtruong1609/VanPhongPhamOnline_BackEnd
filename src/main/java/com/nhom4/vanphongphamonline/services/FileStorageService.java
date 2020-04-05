package com.nhom4.vanphongphamonline.services;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nhom4.vanphongphamonline.config.FileStorage;
import com.nhom4.vanphongphamonline.model.FileData;
import com.nhom4.vanphongphamonline.repository.FileDataRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileStorageService {
	private final Path fileStorageLocation;
	@Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    public FileStorageService(FileStorage fileStorage) {
        this.fileStorageLocation = Paths.get(fileStorage.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
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
    // load từ local
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    //load từ db
    public FileData getFile(String id) {
        if(fileDataRepository.findById(id).equals("Optional.empty")) {
        	return fileDataRepository.findById(id).get();
        }
        return null;
    }
}
