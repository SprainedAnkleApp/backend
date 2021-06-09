package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.cloudstorage.GoogleCloudFileService;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.wallElements.PeakPost;
import pl.edu.agh.ki.io.models.wallElements.reactions.Reaction;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PeakPostResponse extends WallItemResponse {
    private Long id;
    private String content;
    private Peak peak;
    private List<ReactionResponse> reactions;
    private double latitude;
    private double longitude;
    private UserResponse user;
    private String signedUrl;

    public static PeakPostResponse fromPeakPostAndReactions(PeakPost peakPost, List<Reaction> reactions) throws IOException {
        return PeakPostResponse.builder()
                .id(peakPost.getId())
                .content(peakPost.getContent())
                .peak(peakPost.getPeak())
                .latitude(peakPost.getLatitude())
                .longitude(peakPost.getLongitude())
                .user(UserResponse.fromUser(peakPost.getUser()))
                .signedUrl(peakPost.getPhotoPath() != null ? GoogleCloudFileService.generateV4GetObjectSignedUrl(peakPost.getPhotoPath()) : "")
                .reactions(reactions.stream().map(ReactionResponse::fromReaction).collect(Collectors.toList())).build();
    }
}
