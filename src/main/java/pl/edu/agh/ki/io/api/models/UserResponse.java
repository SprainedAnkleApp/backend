package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.models.User;

import java.sql.Date;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePhoto;
    private Date birthday;
    private String about;
    private String phoneNumber;


    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .profilePhoto(user.getProfilePhoto())
                .birthday(user.getBirthday())
                .about(user.getAbout())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}