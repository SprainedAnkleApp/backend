package pl.edu.agh.ki.io.db;

import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.api.models.PhotoResponse;
import pl.edu.agh.ki.io.models.wallElements.Photo;

import java.util.Optional;

@Service
public class PhotoStorage {
    private final PhotoRepository photoRepository;
    private final ReactionsRepository reactionsRepository;

    public PhotoStorage(PhotoRepository photoRepository, ReactionsRepository reactionsRepository){
        this.photoRepository = photoRepository;
        this.reactionsRepository = reactionsRepository;
    }

    public void createPhoto(Photo photo) {
        this.photoRepository.save(photo);
    }


    public PhotoResponse findPhotoById(Long photoId) {
        Optional<Photo> photo = this.photoRepository.findById(photoId);
        if(photo.isEmpty()) return null;
        return PhotoResponse.fromPhotoAndReactions(photo.get(), this.reactionsRepository.findByIdWallElementID(photoId));
    }
}
