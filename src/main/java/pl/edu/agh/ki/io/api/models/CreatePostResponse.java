package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.models.wallElements.Post;
import pl.edu.agh.ki.io.models.wallElements.WallItem;

@Data
@Builder
public class CreatePostResponse {
    private Long id;

    public static CreatePostResponse fromPost(WallItem post) {
        return CreatePostResponse.builder().id(post.getId()).build();
    }
}
