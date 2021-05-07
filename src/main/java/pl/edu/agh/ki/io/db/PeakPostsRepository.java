package pl.edu.agh.ki.io.db;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.wallElements.PeakPost;

import java.util.List;

@Repository
public interface PeakPostsRepository extends JpaRepository<PeakPost, Long> {
    public List<PeakPost> findPeakPostsByPeakId(Long peakId);
}
