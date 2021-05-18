package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ki.io.db.FriendshipStorage;
import pl.edu.agh.ki.io.db.UserStorage;
import pl.edu.agh.ki.io.models.Friendship;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.security.UserPrincipal;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Api(tags = "Friendships")
public class FriendshipsApiController {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;


    @PostMapping("/api/users/{userid}/add") //TODO change response to one with less info
    public ResponseEntity<?> addFriend(@AuthenticationPrincipal User user, @PathVariable("userid") Long addresseeId) {
        User requester = ((UserPrincipal) this.userStorage.loadUserByUsername(user.getLogin())).getUser();
        if(requester.getId().equals(addresseeId)) return ResponseEntity.badRequest().body("You can't add yourself to friends");
        Optional<User> addressee = this.userStorage.findUserById(addresseeId);

        if(addressee.isPresent()) {
            if (this.friendshipStorage.findByRequesterAndAddressee(addressee.get(), requester) != null){
                return acceptFriend(requester, addresseeId);
            }

            Friendship friendship = new Friendship(0, requester, addressee.get());
            this.friendshipStorage.saveFriendship(friendship);
            return new ResponseEntity<>(friendship, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/users/{userid}/accept")
    public ResponseEntity<?> acceptFriend(@AuthenticationPrincipal User user, @PathVariable("userid") Long requesterId) {
        Optional<User> requester = this.userStorage.findUserById(requesterId);
        if(requester.isPresent()) {
            User addressee = ((UserPrincipal) this.userStorage.loadUserByUsername(user.getLogin())).getUser();
            Friendship pendingFriendship = this.friendshipStorage.findByRequesterAndAddressee(requester.get(), addressee);

            if(pendingFriendship != null) {
                pendingFriendship.setStatus(1);
                this.friendshipStorage.saveFriendship(pendingFriendship);

                Friendship friendship = new Friendship(1, addressee, requester.get());
                this.friendshipStorage.saveFriendship(friendship);
                return new ResponseEntity<>(friendship, HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/users/{userid}/reject")
    public ResponseEntity<?> rejectFriend(@AuthenticationPrincipal User user, @PathVariable("userid") Long requesterId) {
        Optional<User> requester = this.userStorage.findUserById(requesterId);
        if(requester.isPresent()) {
            User addressee = ((UserPrincipal) this.userStorage.loadUserByUsername(user.getLogin())).getUser();
            if(this.friendshipStorage.findByRequesterAndAddressee(requester.get(), addressee) != null){
                this.friendshipStorage.deleteByRequesterAndAddressee(requester.get(), addressee);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/users/{userid}/remove")
    public ResponseEntity<Friendship> removeFriend(@AuthenticationPrincipal User user, @PathVariable("userid") Long userId) {
        Optional<User> requester = this.userStorage.findUserById(userId);
        if(requester.isPresent()) {
            User addressee = ((UserPrincipal) this.userStorage.loadUserByUsername(user.getLogin())).getUser();
            if(this.friendshipStorage.findByRequesterAndAddressee(requester.get(), addressee) != null &&
                    this.friendshipStorage.findByRequesterAndAddressee(addressee, requester.get()) != null) {

                this.friendshipStorage.deleteByRequesterAndAddressee(requester.get(), addressee);
                this.friendshipStorage.deleteByRequesterAndAddressee(addressee, requester.get());

                return new ResponseEntity<>(HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

}
