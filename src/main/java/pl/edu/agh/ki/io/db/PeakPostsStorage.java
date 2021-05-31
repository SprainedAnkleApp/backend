package pl.edu.agh.ki.io.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.api.models.PeakPostResponse;
import pl.edu.agh.ki.io.models.wallElements.PeakPost;
import pl.edu.agh.ki.io.models.wallElements.PeakPostPage;

import java.util.Optional;


@Service
public class PeakPostsStorage {
    private final PeakPostsRepository peakPostsRepository;
    private final ReactionsRepository reactionsRepository;
    public PeakPostsStorage(PeakPostsRepository peakPostsRepository, ReactionsRepository reactionsRepository) {
         this.peakPostsRepository = peakPostsRepository;
         this.reactionsRepository = reactionsRepository;
    }

    public Page<PeakPost> findPeakPostsByPeakId(Long peakId, PeakPostPage peakPostPage){
        Sort sort = Sort.by(peakPostPage.getSortDirection(), peakPostPage.getSortBy());
        Pageable pageable = PageRequest.of(peakPostPage.getPageNumber(),
                peakPostPage.getPageSize(), sort);

        return this.peakPostsRepository.findPeakPostsByPeakId(peakId, pageable);
    }

    public PeakPostResponse findPeakPostById(Long peakPostId){
        Optional<PeakPost> peakPost = this.peakPostsRepository.findById(peakPostId);
        if(peakPost.isEmpty()) return null;
        return PeakPostResponse.fromPeakPostAndReactions(peakPost.get(), reactionsRepository.findByIdWallElementID(peakPostId));
    }

    public void createPeakPost(PeakPost peakPost) {
        this.peakPostsRepository.save(peakPost);
    }
}
