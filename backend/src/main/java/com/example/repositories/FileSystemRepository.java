package com.example.repositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

@Repository
public class FileSystemRepository {

    private String RESOURCES_DIR = "vehicle-images/";

    public String save(byte[] content, String imageName) throws IOException{
        Path newFile = Paths.get(RESOURCES_DIR + new Date().getTime() + "-" + imageName);
        Files.createDirectories(newFile.getParent());
         
        Files.write(newFile, content);

        return newFile.toAbsolutePath().toString();
    }   
    
    public FileSystemResource find(String location){
        return new FileSystemResource(Paths.get(location));
    }
}
