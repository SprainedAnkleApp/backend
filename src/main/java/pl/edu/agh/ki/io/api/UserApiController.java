package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.db.GenderStorage;
import pl.edu.agh.ki.io.db.UserStorage;
import pl.edu.agh.ki.io.models.Gender;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.security.AuthenticationProcessingException;
import pl.edu.agh.ki.io.security.UserPrincipal;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Api(tags = "Users")
@RequestMapping("")
public class UserApiController {

    private final UserStorage userStorage;
    private final GenderStorage genderStorage;

    @GetMapping("/api/public/users/me")
    public  User user(@AuthenticationPrincipal User user) {
        UserPrincipal currentUser = (UserPrincipal) this.userStorage.loadUserByUsername(user.getLogin());
        return currentUser.getUser();
    }

    @GetMapping("/api/public/users")
    public List<User> users() {
        return this.userStorage.findAll();
    }

    // TODO: add email verification
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        User newUser = request.toUser();
        Gender userGender = genderStorage.findGenderByLabel(request.getGender());
        newUser.setGender(userGender);
        UserPrincipal userPrincipal;
        try {
            userPrincipal = (UserPrincipal) userStorage.createUser(newUser);
        } catch (AuthenticationProcessingException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(UserResponse.fromUser(userPrincipal.getUser()), HttpStatus.CREATED);
    }

    @GetMapping("/api/public/users/{userid}")
    public ResponseEntity<User> getUser(@PathVariable("userid") Long userid) {
        Optional<User> user = this.userStorage.findUserById(userid);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
