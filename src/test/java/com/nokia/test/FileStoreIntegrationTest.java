package com.nokia.test;

import com.nokia.test.model.File;
import com.nokia.test.model.FileStoreResponseDto;
import com.nokia.test.repo.FileStoreRepo;
import com.nokia.test.service.FileStoreService;
import com.nokia.test.service.impl.FileStoreServiceImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = NokiaApplicationTests.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(print = MockMvcPrint.LOG_DEBUG, printOnlyOnFailure = false)
public class FileStoreIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileStoreService fileStoreService;

    @MockBean
    private FileStoreRepo fileStoreRepo;

    private File file;

    private FileStoreResponseDto fileStoreResponseDto;

    @Value("${file.upload-dir}")
    private String path;

    @Before
    public void setup(){
           file= new File();
           file.setUploadDir(path);
           file.setDocumentFormat("application/g-zip");
           file.setFileName("dummy");

           fileStoreResponseDto=new FileStoreResponseDto();
           fileStoreResponseDto.setResponse("test");
           fileStoreResponseDto.setResponseMessage("Success");

    }

    class FilesWrapper {
        public long copy(InputStream in, Path target, CopyOption... options) throws IOException {
            return Files.copy(in, target, options);
        }
    }


    @Ignore
    @Test
    public void testFileStore() throws Exception {

         FileStoreService fileStoreServiceSpy=Mockito.spy(FileStoreServiceImpl.class);

        MultipartFile mul=new MockMultipartFile("file","file","text/plain","hellow wordl".getBytes());
        HashMap<String, String> contentTypeParams = new HashMap<String, String>();
        contentTypeParams.put("boundary", "265001916915724");
        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);
        Mockito.when(fileStoreRepo.save(file)).thenReturn(file);

        mockMvc.perform(post("/filestore")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("file","D:/filestore/").content(mul.getBytes()).contentType(mediaType)
                .param("fileName","test")).andExpect(status().isOk());
    }

    @Test
    public void getFile() throws Exception {

        Mockito.when(fileStoreRepo.findByFileName("External_test_data")).thenReturn(file);

        mockMvc.perform
                (get("/filestore")
                        .param("fileName","External_test_data")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
