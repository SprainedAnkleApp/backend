package pl.edu.agh.ki.io.db;

import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.wallElements.Photo;

import java.util.Optional;

@Service
public class PhotoStorage {
    private final PhotoRepository photoRepository;

    public PhotoStorage(PhotoRepository photoRepository){
        this.photoRepository = photoRepository;
    }

    public Photo createPhoto(Photo photo) {
        return this.photoRepository.save(photo);
    }


    public Optional<Photo> findPhotoById(Long photoId) {
        return this.photoRepository.findById(photoId);
    }
}
