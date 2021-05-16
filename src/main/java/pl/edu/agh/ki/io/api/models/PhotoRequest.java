package pl.edu.agh.ki.io.api.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter 
public class PhotoRequest {
    private MultipartFile file;
    private String photoPath;
    private String content;
}
