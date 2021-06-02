package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.api.models.*;
import org.springframework.web.reactive.function.client.WebClient;
import pl.edu.agh.ki.io.api.providers.AchievementsProvider;
import pl.edu.agh.ki.io.db.FriendshipStorage;
import pl.edu.agh.ki.io.db.UserStorage;
import pl.edu.agh.ki.io.models.AuthProvider;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.UserPage;
import pl.edu.agh.ki.io.security.AuthenticationProcessingException;
import pl.edu.agh.ki.io.security.UserPrincipal;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController

@Api(tags = "Users")
@RequestMapping("")
@RequiredArgsConstructor
public class UserApiController {

    private final UserStorage userStorage;
    private final OAuth2AuthorizedClientService clientService;
    private final WebClient facebookGraphApiClient;
    private final AchievementsProvider achievementsProvider;
    private final FriendshipStorage friendshipStorage;

    Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @GetMapping("/api/public/users/me")
    public UserResponse user(@AuthenticationPrincipal User user) {
        UserPrincipal currentUser = (UserPrincipal) this.userStorage.loadUserByUsername(user.getLogin());
        return UserResponse.fromUserWithAchievements(currentUser.getUser(), achievementsProvider.getAchievements(user));
    }

    @GetMapping("/api/public/users/me/friends")
    public ResponseEntity<List<UserResponse>> getFriends(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(this.friendshipStorage.findAcceptedForUser(user).stream()
                .map(friendship -> UserResponse.fromUser(friendship.getAddressee()))
                .collect(Collectors.toList()), HttpStatus.OK); // TODO: maybe send noContent when no accepter requests are present
    }

    @GetMapping("/api/public/users/{userId}/friends")
    public ResponseEntity<List<UserResponse>> getUserFriends(@PathVariable("userId") Long userId) {
        Optional<User> user = this.userStorage.findUserById(userId);

        return user.map(u -> new ResponseEntity<>(this.friendshipStorage.findAcceptedForUser(u).stream()
                .map(friendship -> UserResponse.fromUser(friendship.getAddressee()))
                .collect(Collectors.toList()), HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/api/public/users/me/friends/pending")
    public ResponseEntity<List<FriendshipRequestResponse>> getPendingFriendships(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(this.friendshipStorage.findPendingForUser(user).stream()
                .map(FriendshipRequestResponse::fromFriendship)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/fb_friends")
    public ResponseEntity<List<User>> fbFriends(@AuthenticationPrincipal User user) {
        OAuth2AuthorizedClient client =
                clientService.loadAuthorizedClient(
                        AuthProvider.facebook.name(),
                        user.getEmail());

        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        String accessToken = client.getAccessToken().getTokenValue();
        String fbUserId = user.getFacebookUserId();

        WebClient.ResponseSpec responseSpec = facebookGraphApiClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path(fbUserId + "/friends")
                                .queryParam("access_token", accessToken)
                                .build()
                )
                .retrieve();

        FacebookFriendList fbUsers = Objects.requireNonNull(
                responseSpec.bodyToMono(FacebookFriendList.class).block()
        );
        List<FacebookFriend> allFriends = new LinkedList<>(fbUsers.getData());

        while (fbUsers.getNext() != null) {
            fbUsers = Objects.requireNonNull(
                    WebClient.create(fbUsers.getNext()).get()
                            .retrieve()
                            .bodyToMono(FacebookFriendList.class)
                            .block()
            );
            allFriends.addAll(fbUsers.getData());
        }


        logger.info(fbUsers.toString());

        return new ResponseEntity<>(allFriends.stream()
                .map(friend -> userStorage.findUserByFacebookId(friend.getId()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/api/public/users")
    public ResponseEntity<Page<UserResponse>> users(UserPage userPage) {
        Page<User> users = this.userStorage.findAll(userPage);
        Sort sort = Sort.by(userPage.getSortDirection(), userPage.getSortBy());
        Pageable pageable = PageRequest.of(userPage.getPageNumber(),
                userPage.getPageSize(), sort);

        return new ResponseEntity<>(new PageImpl<>(
                    users.stream()
                        .map(UserResponse::fromUser)
                        .collect(Collectors.toList()), pageable, users.getTotalElements()), HttpStatus.OK);
    }

    // TODO: add email verification
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        User newUser = request.toUser();
        UserPrincipal userPrincipal;
        try {
            userPrincipal = (UserPrincipal) userStorage.createUser(newUser);
        } catch (AuthenticationProcessingException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(UserResponse.fromUser(userPrincipal.getUser()), HttpStatus.CREATED);
    }

    @GetMapping("/api/public/users/{userid}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userid") Long userId) {
        Optional<User> optionalUser = this.userStorage.findUserById(userId);
        return optionalUser
                .map(user -> new ResponseEntity<>(UserResponse.fromUser(user), HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
