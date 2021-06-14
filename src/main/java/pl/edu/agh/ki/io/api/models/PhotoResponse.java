package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.cloudstorage.GoogleCloudFileService;
import pl.edu.agh.ki.io.models.wallElements.Photo;
import pl.edu.agh.ki.io.models.wallElements.reactions.Reaction;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
public class PhotoResponse extends WallItemResponse{
    private Long id;
    private String content;
    private String signedUrl;
    private List<ReactionResponse> reactions;
    private double latitude;
    private double longitude;
    private UserResponse user;
    private Long createDate;

    public static PhotoResponse fromPhotoAndReactions(Photo photo, List<Reaction> reactions) {
        return PhotoResponse.builder()
                .id(photo.getId())
                .content(photo.getContent())
                .signedUrl(GoogleCloudFileService.generateV4GetObjectSignedUrl(photo.getPhotoPath()))
                .latitude(photo.getLatitude())
                .longitude(photo.getLongitude())
                .user(UserResponse.fromUser(photo.getUser()))
                .createDate(photo.getCreateDate().getTime())
                .reactions(reactions.stream().map(ReactionResponse::fromReaction).collect(Collectors.toList())).build();
    }
}
