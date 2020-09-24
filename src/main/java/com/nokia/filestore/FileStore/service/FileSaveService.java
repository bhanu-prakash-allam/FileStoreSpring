package com.nokia.filestore.FileStore.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nokia.filestore.FileStore.entity.Files;
import com.nokia.filestore.FileStore.repository.FileStoreRepository;

@Service
public class FileSaveService {
	
	@Autowired
	private FileStoreRepository fileStoreRepository;
	
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		return outputStream.toByteArray();
	}
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
	
	public void StoreFile(MultipartFile file) throws IOException
	{
		
		Files files=new Files();
		files.setFileName(file.getOriginalFilename());
		files.setFileByte(compressBytes(file.getBytes()));
		this.fileStoreRepository.save(files);
	}
	
	public List<Files> getAllFiles()
	{
		return this.fileStoreRepository.findAll();
	}
	public Files getfile(String fileName )
	{
		Files record=this.fileStoreRepository.findByFileName(fileName);
		
		record.setFileByte(decompressBytes(record.getFileByte()));
		return record;
	}
}
