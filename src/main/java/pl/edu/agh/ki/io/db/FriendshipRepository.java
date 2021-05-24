package pl.edu.agh.ki.io.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.Friendship;
import pl.edu.agh.ki.io.models.User;

import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Optional<Friendship> findByRequesterAndAddressee(User requester, User addressee);

    void deleteByRequesterAndAddressee(User requester, User addressee);

}
