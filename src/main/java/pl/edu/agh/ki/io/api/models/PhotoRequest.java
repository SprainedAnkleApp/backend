package pl.edu.agh.ki.io.api.models;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class PhotoRequest {
    private MultipartFile file;
    private String photoPath;
    private String content;
}
