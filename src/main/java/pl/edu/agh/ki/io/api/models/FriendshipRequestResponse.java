package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.models.Friendship;

@Data
@Builder
public class FriendshipRequestResponse {
    private UserResponse user;
    private Long sentDate;

    public static FriendshipRequestResponse fromFriendship(Friendship friendship) {
        return FriendshipRequestResponse.builder()
                .user(UserResponse.fromUser(friendship.getRequester()))
                .sentDate(friendship.getCreateDate().getTime())
                .build();
    }
}
