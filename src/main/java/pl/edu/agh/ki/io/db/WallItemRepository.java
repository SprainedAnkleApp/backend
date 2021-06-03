package pl.edu.agh.ki.io.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.WallItem;

import java.util.Optional;

@Repository
public interface WallItemRepository extends JpaRepository<WallItem, Long> {
    @Override
    Optional<WallItem> findById(Long wallItemId);

    Page<WallItem> findAllByUser(Pageable pageable, User user);
}
