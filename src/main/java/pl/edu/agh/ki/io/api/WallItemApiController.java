package pl.edu.agh.ki.io.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.config.SecurityConfig;
import pl.edu.agh.ki.io.db.UserStorage;
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
    private SecurityConfig securityConfig;
    private UserStorage userStorage;
    public WallItemApiController(WallItemStorage wallItemStorage, SecurityConfig securityConfig, UserStorage userStorage){
        this.wallItemStorage = wallItemStorage;
        this.securityConfig = securityConfig;
        this.userStorage = userStorage;
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
