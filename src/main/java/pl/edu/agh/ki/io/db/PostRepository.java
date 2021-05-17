package pl.edu.agh.ki.io.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.wallElements.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
