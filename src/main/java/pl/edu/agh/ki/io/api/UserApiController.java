package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.resource.HttpResource;
import pl.edu.agh.ki.io.api.models.CreateUserRequest;
import pl.edu.agh.ki.io.api.models.FacebookFriend;
import pl.edu.agh.ki.io.api.models.FacebookFriendList;
import pl.edu.agh.ki.io.api.models.UserResponse;
import pl.edu.agh.ki.io.db.GenderStorage;
import pl.edu.agh.ki.io.db.UserStorage;
import pl.edu.agh.ki.io.models.AuthProvider;
import pl.edu.agh.ki.io.models.Gender;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.security.UserPrincipal;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController

@Api(tags = "Users")
@RequestMapping("/")
public class UserApiController {
    private final UserStorage userStorage;
    private final GenderStorage genderStorage;
    private final OAuth2AuthorizedClientService clientService;
    private final WebClient facebookGraphApiClient;

    Logger logger = LoggerFactory.getLogger(UserApiController.class);

    public UserApiController(UserStorage userStorage, GenderStorage genderStorage,
                             OAuth2AuthorizedClientService clientService, WebClient facebookGraphApiClient) {
        this.userStorage = userStorage;
        this.genderStorage = genderStorage;
        this.clientService = clientService;
        this.facebookGraphApiClient = facebookGraphApiClient;
    }

    @GetMapping("/me")
    public User user(@AuthenticationPrincipal User user) {
        UserPrincipal currentUser = (UserPrincipal) this.userStorage.loadUserByUsername(user.getLogin());
        return currentUser.getUser();
    }

    @GetMapping("/fb_friends")
    public ResponseEntity<List<User>> fbFriends(@AuthenticationPrincipal User user) {
        OAuth2AuthorizedClient client =
                clientService.loadAuthorizedClient(
                        AuthProvider.facebook.name(),
                        user.getEmail());

        if(client == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        String accessToken = client.getAccessToken().getTokenValue();
        String fbUserId = user.getFacebookUserId();

        WebClient.ResponseSpec responseSpec = facebookGraphApiClient.get()
            .uri(uriBuilder ->
                uriBuilder.path(fbUserId +"/friends")
                .queryParam("access_token", accessToken)
                .build()
            )
        .retrieve();

        List<FacebookFriend> fbUsers = Objects.requireNonNull(
                responseSpec.bodyToMono(FacebookFriendList.class).block()
        ).getData();

        logger.info(fbUsers.toString());

        return new ResponseEntity<>(fbUsers.stream()
                .map(friend -> userStorage.findUserByFacebookId(friend.getId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()), HttpStatus.OK);
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

    @GetMapping("/api/public/users/{userid}")
    public ResponseEntity<User> getUser(@PathVariable("userid") Long userid) {
        Optional<User> user = this.userStorage.findUserById(userid);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
