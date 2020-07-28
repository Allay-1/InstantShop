package com.bzbala.ad.bazarbala.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bzbala.ad.bazarbala.exception.BazarBalaDAOException;
import com.bzbala.ad.bazarbala.fileUpload.Helper.FileStorage;
import com.bzbala.ad.bazarbala.fileUpload.Helper.FileUploadService;
import com.bzbala.ad.bazarbala.product.model.DownloadFile;

@RestController
public class SupplierUploadFile {

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	FileStorage fileStorage;

	@CrossOrigin("/**")
	@RequestMapping(value = "/supplier/uploadFile", method = RequestMethod.POST,consumes = {"multipart/form-data"})
	@ResponseBody
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("supplierId") String supplierId,
			@RequestParam("fileType") String fileType) throws BazarBalaDAOException {
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
		Path path = fileUploadService.directoryService(fileStorage, supplierId, fileType);
		String fileName = fileUploadService.storeFile(file, path);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();
		builder.status(Response.Status.OK).entity(fileName + "_" + fileDownloadUri);
		return builder.build();
	}
	
	
	@CrossOrigin("/**")
	@RequestMapping(value = "/supplier/uploadMultipleFiles", method = RequestMethod.POST,consumes = {"multipart/form-data"})
	@ResponseBody
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	 public Response uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("supplierId") String supplierId,
				@RequestParam("fileType") String fileType) throws BazarBalaDAOException{
		ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
	        Arrays.asList(files)
	                .stream()
	                .map(file -> {
						try {
							return uploadFile(file,supplierId,fileType);
						} catch (BazarBalaDAOException e) {
							// TODO Auto-generated catch block
							return "fail"+file;
						}
					})
	                .collect(Collectors.toList());
	         builder.status(Response.Status.OK).entity("Sucess");
	 		return builder.build();
	    }
	
	@CrossOrigin("/**")
	@RequestMapping(value = "/supplier/donwnloadFile", method = RequestMethod.POST)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> downloadFile(HttpServletRequest request,@RequestBody DownloadFile downloadFile) throws BazarBalaDAOException{
		Path path = fileUploadService.directoryService(fileStorage, downloadFile.getSupplierId(), downloadFile.getFileType());
        Resource resource = fileUploadService.loadFileAsResource(downloadFile.getFileName(),path);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
        	 throw new BazarBalaDAOException("File not found ",downloadFile.getFileName());
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .header(HttpHeaders.CONTENT_LOCATION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
