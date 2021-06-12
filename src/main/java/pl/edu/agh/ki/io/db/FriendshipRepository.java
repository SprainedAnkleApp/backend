package pl.edu.agh.ki.io.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.Friendship;
import pl.edu.agh.ki.io.models.User;

import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Optional<Friendship> findByRequesterAndAddressee(User requester, User addressee);

    void deleteByRequesterAndAddressee(User requester, User addressee);

    @Query(value = "select f from Friendship f " +
            "where f.requester.id = :userId and f.status = 1 " +
            "order by f.addressee.firstName, f.addressee.lastName")
    Page<Friendship> findAcceptedByRequesterId(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "select f from Friendship f " +
            "where f.addressee.id = :userId and f.status = 0 " +
            "order by f.addressee.firstName, f.addressee.lastName")
    Page<Friendship> findPendingByAddresseeId(@Param("userId") Long userId, Pageable pageable);
}
