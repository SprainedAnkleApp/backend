package pl.edu.agh.ki.io.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import pl.edu.agh.ki.io.db.WallItemStorage;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.Photo;
import pl.edu.agh.ki.io.models.wallElements.Post;
import pl.edu.agh.ki.io.models.wallElements.WallItem;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/public/wallitems")
public class WallItemApiController {
    private WallItemStorage wallItemStorage;

    public WallItemApiController(WallItemStorage wallItemStorage) {
        this.wallItemStorage = wallItemStorage;
    }

    @GetMapping()
    public List<WallItem> wallItems() {
        return this.wallItemStorage.findAll();
    }

    @GetMapping("/{wallitemid}")
    public Optional<WallItem> getWallItem(@PathVariable("wallitemid") Long wallItemId) {
        return this.wallItemStorage.getWallItemById(wallItemId);
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
    public Long createPost(@RequestParam("file") MultipartFile file, @RequestParam("photoPath") String photoPath,
            @RequestParam("content") String content, @AuthenticationPrincipal User user) throws IOException {
        Photo photo = new Photo(user, content, photoPath);
        wallItemStorage.createPhoto(photo, file);
        return photo.getId();
    }

}
