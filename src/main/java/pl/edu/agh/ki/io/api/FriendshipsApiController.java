package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.api.models.FriendshipRequest;
import pl.edu.agh.ki.io.db.FriendshipStorage;
import pl.edu.agh.ki.io.db.UserStorage;
import pl.edu.agh.ki.io.models.Friendship;
import pl.edu.agh.ki.io.models.User;

import java.util.Optional;

@RestController()
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Api(tags = "Friendships")
@RequestMapping("api/users")
public class FriendshipsApiController {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;


    @PostMapping("/add") //TODO change response to one with less info
    public ResponseEntity<?> addFriend(@AuthenticationPrincipal User requester, @RequestBody FriendshipRequest request) {

        Long addresseeId = request.getId();
        if(requester.getId().equals(addresseeId)) return ResponseEntity.badRequest().body("You can't add yourself to friends");
        Optional<User> addressee = this.userStorage.findUserById(addresseeId);

        if(addressee.isPresent()) {
            if (this.friendshipStorage.findByRequesterAndAddressee(addressee.get(), requester).isPresent()){
                return acceptFriend(requester, request);
            }

            Friendship friendship = new Friendship(0, requester, addressee.get());
            this.friendshipStorage.saveFriendship(friendship);
            return new ResponseEntity<>(friendship, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/accept")
    public ResponseEntity<?> acceptFriend(@AuthenticationPrincipal User addressee, @RequestBody FriendshipRequest request) {
        Long requesterId = request.getId();
        Optional<User> requester = this.userStorage.findUserById(requesterId);
        if(requester.isPresent()) {
            Optional<Friendship> pendingFriendship = this.friendshipStorage.findByRequesterAndAddressee(requester.get(), addressee);

            if(pendingFriendship.isPresent()) {
                Friendship requestedFriendship = pendingFriendship.get();
                requestedFriendship.setStatus(1);
                this.friendshipStorage.saveFriendship(requestedFriendship);

                Friendship friendship = new Friendship(1, addressee, requester.get());
                this.friendshipStorage.saveFriendship(friendship);
                return new ResponseEntity<>(friendship, HttpStatus.OK);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectFriend(@AuthenticationPrincipal User addressee, @RequestBody FriendshipRequest request) {
        Long requesterId = request.getId();
        Optional<User> requester = this.userStorage.findUserById(requesterId);
        if(requester.isPresent()) {
            if(this.friendshipStorage.findByRequesterAndAddressee(requester.get(), addressee).isPresent()){
                this.friendshipStorage.deleteByRequesterAndAddressee(requester.get(), addressee);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Friendship> removeFriend(@AuthenticationPrincipal User addressee, @RequestBody FriendshipRequest request) {
        Long userId = request.getId();
        Optional<User> requester = this.userStorage.findUserById(userId);
        if(requester.isPresent()) {
            if(this.friendshipStorage.findByRequesterAndAddressee(requester.get(), addressee).isPresent() &&
                    this.friendshipStorage.findByRequesterAndAddressee(addressee, requester.get()).isPresent()) {

                this.friendshipStorage.deleteByRequesterAndAddressee(requester.get(), addressee);
                this.friendshipStorage.deleteByRequesterAndAddressee(addressee, requester.get());

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return ResponseEntity.notFound().build();
    }

}
