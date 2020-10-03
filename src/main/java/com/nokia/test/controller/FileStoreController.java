package com.nokia.test.controller;

import com.nokia.test.model.FileStoreResponseDto;
import com.nokia.test.model.FilesResponseDto;
import com.nokia.test.service.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/filestore")
public class FileStoreController {

   @Autowired
    private FileStoreService fileStoreService;


    @PostMapping()
    public ResponseEntity<FileStoreResponseDto> storeFile(@Valid @RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName){
          FileStoreResponseDto fileStoreResponseDto=fileStoreService.storeFile(file,fileName);
          return new ResponseEntity<>(fileStoreResponseDto, HttpStatus.ACCEPTED);
    }

  @GetMapping()
   public ResponseEntity<FileStoreResponseDto> getFile(@Valid @RequestParam("fileName") String fileName
                         ){
      FileStoreResponseDto fileStoreResponseDto=fileStoreService.downloadFile(fileName);
      return new ResponseEntity<>(fileStoreResponseDto, HttpStatus.OK);

    }

    @GetMapping("/files")
    public ResponseEntity<FilesResponseDto> getFileName(){

        FilesResponseDto filesResponseDto= fileStoreService.getFiles();
        return new ResponseEntity<>(filesResponseDto,HttpStatus.OK);

    }
}
