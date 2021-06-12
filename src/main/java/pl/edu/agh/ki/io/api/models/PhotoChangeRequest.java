package pl.edu.agh.ki.io.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class PhotoChangeRequest {
    private MultipartFile profilePhoto;
    private MultipartFile backgroundPhoto;
}
