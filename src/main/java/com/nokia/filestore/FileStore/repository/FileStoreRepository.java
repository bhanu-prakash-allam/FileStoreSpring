package com.nokia.filestore.FileStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nokia.filestore.FileStore.entity.Files;

@Repository
public interface FileStoreRepository extends JpaRepository<Files, Integer> {

	public Files findByFileName(String filename);
}
