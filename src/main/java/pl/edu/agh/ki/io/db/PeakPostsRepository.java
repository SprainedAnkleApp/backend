package pl.edu.agh.ki.io.db;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.wallElements.PeakPost;

@Repository
public interface PeakPostsRepository extends JpaRepository<PeakPost, Long> {
    public Page<PeakPost> findPeakPostsByPeakId(Long peakId, Pageable pageable);
}
