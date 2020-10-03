package com.nokia.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.nio.file.Path;


@Entity
@Table(name = "file")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "file_id")
    private Integer documentId;


    @Column(name = "file_name")
    @NotNull
    @NotBlank
    private String fileName;


    @Column(name = "document_format")
    @NotNull
    @NotBlank
    private String documentFormat;


    @Column(name = "upload_dir")
    @NotNull
    @NotBlank
    private String uploadDir;

}
