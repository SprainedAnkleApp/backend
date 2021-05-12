package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.models.User;

@Data
@Builder
public class UserResponse {
    private Long id;

    static public UserResponse fromUser(User user) {
        return UserResponse.builder().id(user.getId()).build();
    }
}
