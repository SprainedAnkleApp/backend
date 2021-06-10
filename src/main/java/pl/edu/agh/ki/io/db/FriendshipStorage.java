package pl.edu.agh.ki.io.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.Friendship;
import pl.edu.agh.ki.io.models.PageParameters;
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

    public Page<Friendship> findAcceptedForUser(User user, PageParameters pageParameters) {
        Pageable pageable = PageRequest.of(pageParameters.getPageNumber(),
                pageParameters.getPageSize());
        return this.friendshipRepository.findAcceptedByRequesterId(user.getId(),pageable);
    }
    public Page<Friendship> findPendingForUser(User user, PageParameters pageParameters) {
        Pageable pageable = PageRequest.of(pageParameters.getPageNumber(),
                pageParameters.getPageSize());
        return this.friendshipRepository.findPendingByAddresseeId(user.getId(), pageable);
    }

    public boolean areFriends(User user1, User user2) {
        return (this.friendshipRepository.findByRequesterAndAddressee(user1, user2).isPresent() &&
                this.friendshipRepository.findByRequesterAndAddressee(user2, user1).isPresent());
    }
}
