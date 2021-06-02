package pl.edu.agh.ki.io.db;

import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.Friendship;
import pl.edu.agh.ki.io.models.User;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FriendshipStorage {
    private final FriendshipRepository friendshipRepository;

    public FriendshipStorage(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public void saveFriendship(Friendship friendship) {
        this.friendshipRepository.save(friendship);
    }

    public Optional<Friendship> findByRequesterAndAddressee(User requester, User addressee){
        return this.friendshipRepository.findByRequesterAndAddressee(requester, addressee);
    }

    public void deleteByRequesterAndAddressee(User requester, User addressee){
        this.friendshipRepository.deleteByRequesterAndAddressee(requester, addressee);
    }

    public List<Friendship> findAcceptedForUser(User user) {
        return this.friendshipRepository.findAcceptedByRequesterId(user.getId());
    }
    public List<Friendship> findPendingForUser(User user) {
        return this.friendshipRepository.findPendingByAddresseeId(user.getId());
    }
}
