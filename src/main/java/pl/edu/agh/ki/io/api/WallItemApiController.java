package pl.edu.agh.ki.io.api;


import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ki.io.api.models.WallItemResponse;
import pl.edu.agh.ki.io.db.WallItemStorage;
import pl.edu.agh.ki.io.models.wallElements.WallItemPage;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/public/wallitems")
@Api(tags = "Wall Items")
public class WallItemApiController {
    private final WallItemStorage wallItemStorage;

    public WallItemApiController(WallItemStorage wallItemStorage) {
        this.wallItemStorage = wallItemStorage;
    }


    @GetMapping()
    public ResponseEntity<Page<WallItemResponse>> getWallItems(WallItemPage wallItemPage) {
        return new ResponseEntity<>(this.wallItemStorage.findAll(wallItemPage), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<WallItemResponse>> getUserWallItems(@PathVariable("userId") Long userId, WallItemPage wallItemPage) {
        return new ResponseEntity<>(this.wallItemStorage.findUserWallItems(wallItemPage, userId), HttpStatus.OK);
    }
}
