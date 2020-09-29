package com.nokia.filestore.FileStore.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nokia.filestore.FileStore.entity.Files;
import com.nokia.filestore.FileStore.service.FileSaveService;

/* comments added in the controller*/
@RestController
@CrossOrigin("*")
public class FileStoreController {

	@Autowired
	private FileSaveService fileSaveService;
	
	
	
	@PostMapping("/store")
	public String storeFile(@RequestParam("file") MultipartFile file) throws IOException
	{
		
		this.fileSaveService.StoreFile(file);
		return "stored";
	}
	
	@GetMapping("/get/files")
	public List<Files> getFile()
	{
		return this.fileSaveService.getAllFiles();
	}
	@GetMapping("/download/{fileName}")
	public ResponseEntity<byte[]> getRawFile(@PathVariable String fileName)
	{
		Files files=this.fileSaveService.getfile(fileName);
		
		byte[] isr=files.getFileByte();
		HttpHeaders respHeaders = new HttpHeaders();
		String url= files.getFileName();
		respHeaders.setContentLength(isr.length);
		respHeaders.set("url",url);
		respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		return new ResponseEntity<byte[]>(isr, respHeaders, HttpStatus.OK);
	}
}
