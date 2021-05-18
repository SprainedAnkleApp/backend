package pl.edu.agh.ki.io.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.Friendship;
import pl.edu.agh.ki.io.models.User;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Friendship findByRequesterAndAddressee(User requester, User addressee);

    void deleteByRequesterAndAddressee(User requester, User addressee);

}
