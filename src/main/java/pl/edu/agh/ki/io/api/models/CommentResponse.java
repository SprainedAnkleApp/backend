package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.cloudstorage.GoogleCloudFileService;
import pl.edu.agh.ki.io.models.Comment;
import pl.edu.agh.ki.io.models.User;

import java.io.IOException;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private Long wallItemId;
    private UserResponse user;
    public static CommentResponse fromComment(Comment comment) throws IOException{
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .wallItemId(comment.getWallItem().getId())
                .user(UserResponse.fromUser(comment.getUser()))
                .build();
    }
}
