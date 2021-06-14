package pl.edu.agh.ki.io.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.Comment;
import pl.edu.agh.ki.io.models.wallElements.WallItem;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByWallItem(Pageable pageable, WallItem wallItem);
    List<Comment> findAllByWallItem(WallItem wallItem);
}
