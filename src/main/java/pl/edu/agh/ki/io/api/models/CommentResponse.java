package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.cloudstorage.GoogleCloudFileService;
import pl.edu.agh.ki.io.models.Comment;

import java.io.IOException;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private Long wallItemId;
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String signedUrl;

    public static CommentResponse fromComment(Comment comment) throws IOException {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .wallItemId(comment.getWallItem().getId())
                .userId(comment.getUser().getId())
                .userFirstName(comment.getUser().getFirstName())
                .userLastName(comment.getUser().getLastName())
                .signedUrl(comment.getUser().getProfilePhoto().startsWith("http") ?
                        comment.getUser().getProfilePhoto() :
                        GoogleCloudFileService.generateV4GetObjectSignedUrl(comment.getUser().getProfilePhoto()))
                .build();
    }
}
