package pl.edu.agh.ki.io.api;


import com.google.cloud.storage.StorageException;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ki.io.api.models.PhotoResponse;
import pl.edu.agh.ki.io.cloudstorage.GoogleCloudFileService;
import pl.edu.agh.ki.io.db.WallItemStorage;
import pl.edu.agh.ki.io.models.wallElements.Photo;
import pl.edu.agh.ki.io.models.wallElements.WallItem;
import pl.edu.agh.ki.io.models.wallElements.WallItemPage;

import java.io.FileNotFoundException;
import java.io.IOException;


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
    public ResponseEntity<Page<WallItem>> getWallItems(WallItemPage wallItemPage) throws StorageException, FileNotFoundException, IOException {
        return new ResponseEntity<>(this.wallItemStorage.findAll(wallItemPage).map(
                wallItem -> {
                    if (wallItem instanceof Photo) {
                        Photo photo = (Photo) wallItem;
                        try {
                            photo.setPhotoPath(GoogleCloudFileService.generateV4GetObjectSignedUrl(photo.getPhotoPath()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return (WallItem) photo;
                    }
                    return wallItem;
                }
        ), HttpStatus.OK);
    }
}
