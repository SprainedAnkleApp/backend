package pl.edu.agh.ki.io.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.wallElements.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
