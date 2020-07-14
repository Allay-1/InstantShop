package com.bzbala.ad.bazarbala.fileUpload.Helper;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.bzbala.ad.bazarbala.exception.BazarBalaDAOException;

@Service
public class FileUploadService {

	
	
	public Path directoryService(FileStorage fileStorageProperties, String supplierId, String fileType)
			throws BazarBalaDAOException {

		Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir() + supplierId + fileType)
				.toAbsolutePath().normalize();

		try {
			Files.createDirectories(fileStorageLocation);
			return fileStorageLocation;
		} catch (Exception ex) {
			throw new BazarBalaDAOException("Could not create the directory where the uploaded files will be stored.",
					ex.getMessage(), ex.getCause());
		}
	}

	public String storeFile(MultipartFile file,Path fileStorageLocation) throws BazarBalaDAOException {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		if (fileName.contains("..")) {
			throw new BazarBalaDAOException("this file have some issue it has invalid name" + fileName, "file issue");
		}

		Path targetLocation = fileStorageLocation.resolve(fileName);
		try {
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fileName;
	}
	
	public Resource loadFileAsResource(String fileName,Path fileStorageLocation) throws BazarBalaDAOException{
        try {
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new BazarBalaDAOException("File not found " + fileName,"loadFileAsResource");
            }
        } catch (MalformedURLException ex) {
            throw new BazarBalaDAOException("File not found " + fileName, ex.getMessage(),ex.getCause());
        }
    }

}
