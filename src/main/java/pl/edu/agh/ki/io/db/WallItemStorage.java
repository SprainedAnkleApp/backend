package pl.edu.agh.ki.io.db;

import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.wallElements.WallItem;

import java.util.List;
import java.util.Optional;

@Service
public class WallItemStorage {
    private final WallItemRepository wallItemRepository;

    public WallItemStorage(WallItemRepository wallItemRepository, PostRepository postRepository, PhotoRepository photoRepository){
        this.wallItemRepository = wallItemRepository;
    }

    public List<WallItem> findAll() {
        return this.wallItemRepository.findAll();
    }

    public Optional<WallItem> getWallItemById(Long wallItemId) {
        return this.wallItemRepository.findById(wallItemId);
    }

}
