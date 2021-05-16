package pl.edu.agh.ki.io.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.wallElements.WallItem;
import pl.edu.agh.ki.io.models.wallElements.WallItemPage;
import java.util.Optional;

@Service
public class WallItemStorage {
    private final WallItemRepository wallItemRepository;

    public WallItemStorage(WallItemRepository wallItemRepository, PostRepository postRepository, PhotoRepository photoRepository){
        this.wallItemRepository = wallItemRepository;
    }

    public Page<WallItem> findAll(WallItemPage wallItemPage) {
        Sort sort = Sort.by(wallItemPage.getSortDirection(), wallItemPage.getSortBy());

        Pageable pageable = PageRequest.of(wallItemPage.getPageNumber(),
                wallItemPage.getPageSize(), sort);
        return this.wallItemRepository.findAll(pageable);
    }

    public Optional<WallItem> getWallItemById(Long wallItemId) {
        return this.wallItemRepository.findById(wallItemId);
    }

}
