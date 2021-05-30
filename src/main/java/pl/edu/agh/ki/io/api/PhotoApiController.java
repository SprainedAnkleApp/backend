package pl.edu.agh.ki.io.api;

import com.google.cloud.storage.StorageException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.api.models.PhotoRequest;
import pl.edu.agh.ki.io.api.models.PhotoResponse;
import pl.edu.agh.ki.io.cloudstorage.GoogleCloudFileService;
import pl.edu.agh.ki.io.db.PhotoStorage;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.Photo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Api(tags = "Photos")
@RequestMapping("api/public/photos")
public class PhotoApiController {
    private final PhotoStorage photoStorage;
    private final GoogleCloudFileService fileService;

    public PhotoApiController(PhotoStorage photoStorage, GoogleCloudFileService fileService) {
        this.photoStorage = photoStorage;
        this.fileService = fileService;
    }

    @GetMapping("/{photoid}")
    public ResponseEntity<PhotoResponse> getPhoto(@PathVariable("photoid") Long photoId) throws StorageException, FileNotFoundException, IOException {
        Optional<Photo> photo = this.photoStorage.findPhotoById(photoId);
        if (photo.isPresent()) {
            String content = photo.get().getContent();
            String signedUrl = GoogleCloudFileService.generateV4GetObjectSignedUrl(photo.get().getPhotoPath());

            return ResponseEntity.ok(new PhotoResponse(content, signedUrl));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/photo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public Long createPhoto(@ModelAttribute PhotoRequest photo, @AuthenticationPrincipal User user) throws IOException {
        String photoPath =  GoogleCloudFileService.generateFileName();
        fileService.upload(photo.getFile(), photoPath);

        Photo photoDbEntry = new Photo(user, photo.getContent(), photoPath);
        photoDbEntry = this.photoStorage.createPhoto(photoDbEntry);
        return photoDbEntry.getId();
    }

}
