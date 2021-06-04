package pl.edu.agh.ki.io.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class CreatePostRequest {
    private MultipartFile file;
    private String content;
    private double latitude;
    private double longitude;
}
