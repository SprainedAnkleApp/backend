package pl.edu.agh.ki.io.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.validation.PasswordMatches;
import pl.edu.agh.ki.io.validation.SimpleSqlProtected;
import pl.edu.agh.ki.io.validation.ValidEmail;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@NoArgsConstructor
@PasswordMatches
public class CreateUserRequest {

    @NotNull
    @SimpleSqlProtected
    private String username;

    @NotNull
    @SimpleSqlProtected
    private String password;
    private String matchingPassword;

    @NotNull
    @SimpleSqlProtected
    private String firstName;

    @NotNull
    @SimpleSqlProtected
    private String lastName;

    private Date birthday;

    private String profilePhoto;

    private String phoneNumber;

    @NotNull
    @ValidEmail
    @SimpleSqlProtected
    private String email;

    @NotNull
    @SimpleSqlProtected
    private String gender;

    public User toUser() {
        User user = new User(
                this.username,
                this.password,
                this.firstName,
                this.lastName,
                this.email
        );
        user.setBirthday(this.birthday);
        user.setProfilePhoto(this.profilePhoto);
        user.setPhoneNumber(this.phoneNumber);

        return user;
    }
}
