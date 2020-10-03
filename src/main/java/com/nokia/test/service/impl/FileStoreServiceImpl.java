package com.nokia.test.service.impl;

import com.nokia.test.model.File;
import com.nokia.test.model.FileStoreResponseDto;
import com.nokia.test.model.FilesResponseDto;
import com.nokia.test.repo.FileStoreRepo;
import com.nokia.test.service.FileStoreService;
import com.nokia.test.util.TarManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileStoreServiceImpl implements FileStoreService {

    private static Path fileStorageLocation;

    @Autowired
    private FileStoreRepo fileStoreRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    TarManager tarManager;

    @Value("${file.upload-dir}")
    private String path;




    @Override
    public FileStoreResponseDto storeFile(MultipartFile file, String fileName) {
        FileStoreResponseDto fileStoreResponseDto=null;
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation=null;
        try {
             createDirs(new File());
             targetLocation = fileStorageLocation.resolve(originalFileName);
             copyFile(file,targetLocation);
             File fileEntity=fileStoreRepo.findByFileName(fileName);
            if(fileEntity != null) {
                throw new RuntimeException("File already exists with fileName-"+fileName);
            }

                File fileObj = new File();
                fileObj.setFileName(fileName);
                fileObj.setDocumentFormat(file.getContentType());
                fileObj.setUploadDir(targetLocation.toString());
                fileStoreRepo.save(fileObj);

                fileStoreResponseDto= new FileStoreResponseDto();
                fileStoreResponseDto.setResponse(fileName);
                fileStoreResponseDto.setResponseMessage("Success");

            return fileStoreResponseDto;

        } catch (IOException ex) {

            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);

        }catch (DataAccessException ex){
            throw new RuntimeException("Error while inserting data");
        }
    }

    @Override
    public FileStoreResponseDto downloadFile(String fileName) {
        FileStoreResponseDto fileStoreResponseDto=null;
        Path filePath =null;
        try {
            if(fileName.endsWith(".tar.gz")) {
                filePath = Paths.get(path.concat(fileName)).toAbsolutePath().normalize();
            }{
                filePath = Paths.get(path.concat(fileName.concat(".tar.gz"))).toAbsolutePath().normalize();
            }
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                fileStoreResponseDto=new FileStoreResponseDto();
                fileStoreResponseDto.setResponse("Success");
                fileStoreResponseDto.setResource(filePath);
                return fileStoreResponseDto;
            } else {

                throw new FileNotFoundException("File not found " + fileName);

            }

        } catch (MalformedURLException | FileNotFoundException ex) {

            throw new RuntimeException("File not found " + fileName);

        }

    }

    @Override
    public FilesResponseDto getFiles() {
        FilesResponseDto filesResponseDto=null;
        try{
            List<File> files=fileStoreRepo.findAll();
            if(files.isEmpty()){
                throw new RuntimeException("Data not found exception");
            }
            filesResponseDto=new FilesResponseDto();
            filesResponseDto.setFiles(files);

        }catch (DataAccessException ex){
            throw new RuntimeException("Error occurred while retrieving");
        }
        return filesResponseDto;
    }

    private void createDirs(File fileStorageProperties) {

        fileStorageLocation = Paths.get(path)
                .toAbsolutePath().normalize();
        try {

            Files.createDirectories(fileStorageLocation);

        } catch (Exception ex) {

            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);

        }

    }

    private void copyFile(MultipartFile file, Path targetLocation) throws IOException {
        if(file!=null) {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}

