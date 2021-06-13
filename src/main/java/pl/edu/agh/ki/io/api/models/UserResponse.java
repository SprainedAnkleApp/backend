package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.api.providers.AchievementsProvider;
import pl.edu.agh.ki.io.cloudstorage.GoogleCloudFileService;
import pl.edu.agh.ki.io.models.User;

import java.sql.Date;
import java.util.List;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePhoto;
    private  String backgroundPhoto;
    private Date birthday;
    private String about;
    private String phoneNumber;
    private boolean isFriend;
    private List<AchievementsProvider.Achievement> achievements;


    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .profilePhoto((user.getProfilePhoto() == null || user.getProfilePhoto().startsWith("http")) ? user.getProfilePhoto() : GoogleCloudFileService.generateV4GetObjectSignedUrl(user.getProfilePhoto()))
                .backgroundPhoto((user.getBackgroundPhoto() == null || user.getBackgroundPhoto().startsWith("http")) ? user.getBackgroundPhoto() : GoogleCloudFileService.generateV4GetObjectSignedUrl(user.getBackgroundPhoto()))
                .birthday(user.getBirthday())
                .about(user.getAbout())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public static UserResponse fromUserWithAchievements(User user, List<AchievementsProvider.Achievement> achievements) {
        return UserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .profilePhoto((user.getProfilePhoto() == null || user.getProfilePhoto().startsWith("http")) ? user.getProfilePhoto() : GoogleCloudFileService.generateV4GetObjectSignedUrl(user.getProfilePhoto()))
                .backgroundPhoto((user.getBackgroundPhoto() == null || user.getBackgroundPhoto().startsWith("http")) ? user.getBackgroundPhoto() : GoogleCloudFileService.generateV4GetObjectSignedUrl(user.getBackgroundPhoto()))
                .birthday(user.getBirthday())
                .about(user.getAbout())
                .phoneNumber(user.getPhoneNumber())
                .achievements(achievements)
                .build();
    }

    public static UserResponse fromUserWithFriendship(User user, boolean areFriends) {
        return UserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .profilePhoto((user.getProfilePhoto() == null || user.getProfilePhoto().startsWith("http")) ? user.getProfilePhoto() : GoogleCloudFileService.generateV4GetObjectSignedUrl(user.getProfilePhoto()))
                .backgroundPhoto((user.getBackgroundPhoto() == null || user.getBackgroundPhoto().startsWith("http")) ? user.getBackgroundPhoto() : GoogleCloudFileService.generateV4GetObjectSignedUrl(user.getBackgroundPhoto()))
                .birthday(user.getBirthday())
                .about(user.getAbout())
                .phoneNumber(user.getPhoneNumber())
                .isFriend(areFriends)
                .build();
    }
}