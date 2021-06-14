package pl.edu.agh.ki.io.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.WallItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface WallItemRepository extends JpaRepository<WallItem, Long> {
    @Override
    Optional<WallItem> findById(Long wallItemId);

    @Query(value = "select wi from WallItem wi " +
            "where wi.user.id = :current_userid or wi.id in :friend_ids " +
            "order by wi.createDate desc")
    Page<WallItem> findAllUserWallItems(@Param("current_userid") Long userId,
                                        @Param("friend_ids") List<Long> friendsIds,
                                        Pageable pageable);
}
