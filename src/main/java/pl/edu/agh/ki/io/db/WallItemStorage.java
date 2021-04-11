package pl.edu.agh.ki.io.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pl.edu.agh.ki.io.cloudstorage.GoogleCloudFileService;
import pl.edu.agh.ki.io.models.wallElements.Photo;
import pl.edu.agh.ki.io.models.wallElements.Post;
import pl.edu.agh.ki.io.models.wallElements.WallItem;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class WallItemStorage {
    WallItemRepository wallItemRepository;
    PhotoRepository photoRepository;
    PostRepository postRepository;

    public WallItemStorage(WallItemRepository wallItemRepository, PostRepository postRepository,
            PhotoRepository photoRepository) {
        this.wallItemRepository = wallItemRepository;
        this.photoRepository = photoRepository;
        this.postRepository = postRepository;
    }

    public List<WallItem> findAll() {
        return this.wallItemRepository.findAll();
    }

    public Optional<WallItem> getWallItemById(Long wallItemId) {
        return this.wallItemRepository.findById(wallItemId);
    }

    public void createPhoto(Photo photo) {
        this.photoRepository.save(photo);
    }

    public void createPost(Post post) {
        this.postRepository.save(post);
    }
}
