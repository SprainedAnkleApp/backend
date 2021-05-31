package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WallItemResponse {
    UserResponse userResponse;
    String content;
    String photoPath;
}
