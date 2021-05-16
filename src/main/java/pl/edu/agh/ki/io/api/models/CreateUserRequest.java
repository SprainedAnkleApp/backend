package pl.edu.agh.ki.io.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.ki.io.models.AuthProvider;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.validation.PasswordMatches;
import pl.edu.agh.ki.io.validation.SimpleSqlProtected;
import pl.edu.agh.ki.io.validation.ValidEmail;
import pl.edu.agh.ki.io.validation.ValidPassword;

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
    @ValidPassword
    private String password;
    private String matchingPassword;

    @NotNull
    @SimpleSqlProtected
    private String firstName;

    @NotNull
    @SimpleSqlProtected
    private String lastName;

    @NotNull
    @ValidEmail
    @SimpleSqlProtected
    private String email;

    @SimpleSqlProtected
    private String profilePhoto;

    private Date birthday;

    @SimpleSqlProtected
    private String phoneNumber;

    @NotNull
    @SimpleSqlProtected
    private String gender;

    public User toUser() {
        return new User(
                this.username,
                this.password,
                AuthProvider.local,
                this.firstName,
                this.lastName,
                this.email,
                this.profilePhoto,
                this.birthday,
                this.phoneNumber
        );
    }
}
