package pl.edu.agh.ki.io.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.db.WallItemStorage;
import pl.edu.agh.ki.io.models.wallElements.Photo;
import pl.edu.agh.ki.io.models.wallElements.Post;
import pl.edu.agh.ki.io.models.wallElements.WallItem;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/public/wallitems")
public class WallItemApiController {
    private WallItemStorage wallItemStorage;

    public WallItemApiController(WallItemStorage wallItemStorage){
        this.wallItemStorage = wallItemStorage;
    }

    @GetMapping()
    public List<WallItem> wallItems() {
        return this.wallItemStorage.findAll();
    }

    @GetMapping("/{wallitemid}")
    public Optional<WallItem> getWallItem(@PathVariable("wallitemid") Long wallItemId){
        return this.wallItemStorage.getWallItemById(wallItemId);
    }

    //TODO find logged in user and assign post/photo to him
    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createPost(@RequestBody Post post){
        wallItemStorage.createPost(post);
        return post.getId();
    }

    @PostMapping("/photo")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createPost(@RequestBody Photo photo){
        wallItemStorage.createPhoto(photo);
        return photo.getId();
    }

}
