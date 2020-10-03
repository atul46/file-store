package com.nokia.test.service;

import com.nokia.test.model.FileStoreResponseDto;
import com.nokia.test.model.FilesResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileStoreService {

    public FileStoreResponseDto storeFile(MultipartFile file, String fileName);

    public FileStoreResponseDto downloadFile(String fileName);

    public FilesResponseDto getFiles();
}
