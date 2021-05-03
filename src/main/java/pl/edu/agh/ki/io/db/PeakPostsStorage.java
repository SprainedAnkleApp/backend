package pl.edu.agh.ki.io.db;

import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.wallElements.PeakPost;

import java.util.List;

@Service
public class PeakPostsStorage {
    private final PeakPostsRepository peakPostsRepository;

    public PeakPostsStorage(PeakPostsRepository peakPostsRepository) {
         this.peakPostsRepository = peakPostsRepository;
    }

    public List<PeakPost> findPeakPostsByPeakId(Long peakId){
        return this.peakPostsRepository.findPeakPostsByPeakId(peakId);
    }

    public void createPeakPost(PeakPost peakPost) {
        this.peakPostsRepository.save(peakPost);
    }
}
