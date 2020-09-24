package com.nokia.filestore.FileStore.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

	private final Path rootLocation = Paths.get("src/main/resources/static");
	public void store(MultipartFile file) {
		try {
			
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
			
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("FAIL!");
		}
	}
}
