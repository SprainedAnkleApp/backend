package pl.edu.agh.ki.io.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class PhotoResponse {
    private String content;
    private String signedUrl;

    public PhotoResponse(String content, String signedUrl) {
        this.content = content;
        this.signedUrl = signedUrl;
    }
}
