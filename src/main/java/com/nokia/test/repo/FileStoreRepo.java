package com.nokia.test.repo;

import com.nokia.test.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStoreRepo extends JpaRepository<File,String> {


    File findByFileName(String fileName);

}
