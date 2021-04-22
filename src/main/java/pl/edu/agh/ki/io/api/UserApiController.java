package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.db.GenderStorage;
import pl.edu.agh.ki.io.db.UserStorage;
import pl.edu.agh.ki.io.models.Gender;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.security.UserPrincipal;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Api(tags = "Users")
@RequestMapping("/")
public class UserApiController {

    private final UserStorage userStorage;
    private final GenderStorage genderStorage;

    @GetMapping("/me")
    public  User user(@AuthenticationPrincipal User user) {
        UserPrincipal currentUser = (UserPrincipal) this.userStorage.loadUserByUsername(user.getLogin());
        return currentUser.getUser();
    }

    @GetMapping("api/public/users")
    public List<User> users() {
        return this.userStorage.findAll();
    }

    // TODO: add email verification
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid CreateUserRequest request) {
        User newUser = request.toUser();
        Gender userGender = genderStorage.findGenderByLabel(request.getGender());
        newUser.setGender(userGender);

        UserPrincipal userPrincipal = (UserPrincipal) userStorage.createUser(newUser);

        return UserResponse.fromUser(userPrincipal.getUser());
    }
}
