package pl.edu.agh.ki.io.db;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PeakPostsStorage {
    private final PeakPostsRepository peakPostsRepository;

    public Page<PeakPost> findPeakPostsByPeakId(Long peakId, PeakPostPage peakPostPage){
        Sort sort = Sort.by(peakPostPage.getSortDirection(), peakPostPage.getSortBy());
        Pageable pageable = PageRequest.of(peakPostPage.getPageNumber(),
                peakPostPage.getPageSize(), sort);

        return this.peakPostsRepository.findPeakPostsByPeakId(peakId, pageable);
    }

    public Optional<PeakPost> findPeakPostById(Long peakPostId) {
        return this.peakPostsRepository.findById(peakPostId);
    }

    public PeakPost createPeakPost(PeakPost peakPost) {
        return this.peakPostsRepository.save(peakPost);
    }
}
