package pl.edu.agh.ki.io.db;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.api.models.PeakPostResponse;
import pl.edu.agh.ki.io.models.wallElements.PeakPost;
import pl.edu.agh.ki.io.models.wallElements.PeakPostPage;

import java.io.IOException;
import java.util.Optional;


@Service
public class PeakPostsStorage {
    private final PeakPostsRepository peakPostsRepository;
    private final ReactionsRepository reactionsRepository;
    public PeakPostsStorage(PeakPostsRepository peakPostsRepository, ReactionsRepository reactionsRepository) {
         this.peakPostsRepository = peakPostsRepository;
         this.reactionsRepository = reactionsRepository;
    }

    public Page<PeakPostResponse> findPeakPostsByPeakId(Long peakId, PeakPostPage peakPostPage){
        Sort sort = Sort.by(peakPostPage.getSortDirection(), peakPostPage.getSortBy());
        Pageable pageable = PageRequest.of(peakPostPage.getPageNumber(),
                peakPostPage.getPageSize(), sort);
        return this.peakPostsRepository.findPeakPostsByPeakId(peakId, pageable)
                .map(peakPost -> {
                    try {
                        return PeakPostResponse.fromPeakPostAndReactions(peakPost, this.reactionsRepository.findByIdWallElementID(peakPost.getId()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    public PeakPostResponse findPeakPostById(Long peakPostId) throws IOException {
        Optional<PeakPost> peakPost = this.peakPostsRepository.findById(peakPostId);
        if(peakPost.isEmpty()) return null;
        return PeakPostResponse.fromPeakPostAndReactions(peakPost.get(), reactionsRepository.findByIdWallElementID(peakPostId));
    }

    public void createPeakPost(PeakPost peakPost) {
        this.peakPostsRepository.save(peakPost);
    }
}
