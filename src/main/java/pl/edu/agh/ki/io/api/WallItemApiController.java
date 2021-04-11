package pl.edu.agh.ki.io.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import pl.edu.agh.ki.io.api.models.PhotoRequest;
import pl.edu.agh.ki.io.api.models.PhotoResponse;
import pl.edu.agh.ki.io.cloudstorage.GoogleCloudFileService;
import pl.edu.agh.ki.io.db.WallItemStorage;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.Photo;
import pl.edu.agh.ki.io.models.wallElements.Post;
import pl.edu.agh.ki.io.models.wallElements.WallItem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.google.auth.oauth2.IdTokenProvider.Option;
import com.google.cloud.storage.StorageException;

@RestController
@RequestMapping("api/public/wallitems")
public class WallItemApiController {
    private WallItemStorage wallItemStorage;
    @Autowired
    GoogleCloudFileService fileService;

    public WallItemApiController(WallItemStorage wallItemStorage) {
        this.wallItemStorage = wallItemStorage;
    }

    @GetMapping()
    public List<WallItem> wallItems() {
        return this.wallItemStorage.findAll();
    }

    @GetMapping("post/{postid}")
    public ResponseEntity<Post> getPost(@PathVariable("postid") Long postId) {
        Optional<WallItem> wallitem = this.wallItemStorage.getWallItemById(postId);
        if (wallitem.isPresent()) {
            if (wallitem.get() instanceof Post) {
                Post post = (Post) wallitem.get();
                return ResponseEntity.ok(post);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/photo/{photoid}")
    public ResponseEntity<PhotoResponse> getPhoto(@PathVariable("photoid") Long photoid) throws StorageException, FileNotFoundException, IOException {
        Optional<WallItem> wallitem = this.wallItemStorage.getWallItemById(photoid);
        if (wallitem.isPresent()) {
            if (wallitem.get() instanceof Photo) {
                Photo photo = (Photo) wallitem.get();

                String content = (String)photo.getContent();
                String signedUrl = GoogleCloudFileService.generateV4GetObjectSignedUrl(photo.getPhotoPath());

                return ResponseEntity.ok(new PhotoResponse(content, signedUrl));
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createPost(@RequestBody Post post, @AuthenticationPrincipal User user) {
        post.setUser(user);
        wallItemStorage.createPost(post);
        return post.getId();
    }

    @PostMapping(value = "/photo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public Long createPhoto(@ModelAttribute PhotoRequest photo, @AuthenticationPrincipal User user) throws IOException {
        Photo photoDbEntry = new Photo(user, photo.getContent(), photo.getPhotoPath());
        wallItemStorage.createPhoto(photoDbEntry);

        fileService.upload(photo.getFile(), GoogleCloudFileService.generateFileName());

        return photoDbEntry.getId();
    }

}
