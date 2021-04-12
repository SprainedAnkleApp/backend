package pl.edu.agh.ki.io.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.db.GenderStorage;
import pl.edu.agh.ki.io.db.UserStorage;
import pl.edu.agh.ki.io.models.Gender;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.security.UserPrincipal;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class UserApiController {

    private UserStorage userStorage;
    private GenderStorage genderStorage;

    public UserApiController(UserStorage userStorage, GenderStorage genderStorage) {
        this.userStorage = userStorage;
        this.genderStorage = genderStorage;
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
