package pl.edu.agh.ki.io.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLogin(String login);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByFacebookUserId(String facebookId);

    @Query(value = "select u from User u " +
            "where lower(u.firstName) like concat('%', lower(:searchTerm), '%') " +
                "or lower(u.lastName) like concat('%', lower(:searchTerm), '%' ) " +
            "order by u.firstName, u.lastName")
    Page<User> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);
}
