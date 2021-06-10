package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.api.models.CreatePostRequest;
import pl.edu.agh.ki.io.api.models.PeakPostResponse;
import pl.edu.agh.ki.io.cloudstorage.GoogleCloudFileService;
import pl.edu.agh.ki.io.db.PeakPostsStorage;
import pl.edu.agh.ki.io.db.PeakStorage;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.PeakPost;
import pl.edu.agh.ki.io.models.wallElements.PeakPostPage;
import java.io.IOException;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Api(tags = "PeakPosts")
@RequestMapping("api/public/peak")
public class PeakPostsApiController {

    private final PeakPostsStorage peakPostsStorage;
    private final PeakStorage peakStorage;
    private final GoogleCloudFileService fileService;

    @GetMapping("{peakid}/posts")
    public ResponseEntity<Page<PeakPostResponse>> getPeakPostsByPeakId(@PathVariable("peakid") Long peakId, PeakPostPage peakPostPage) {
        if (this.peakStorage.findPeakById(peakId).isPresent())
            return new ResponseEntity<>(this.peakPostsStorage.findPeakPostsByPeakId(peakId, peakPostPage), HttpStatus.OK);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/{peakid}/posts", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PeakPostResponse> createPost(@ModelAttribute CreatePostRequest postRequest, @AuthenticationPrincipal User user, @PathVariable("peakid") Long peakId) throws IOException {
        String photoPath = "";
        if (postRequest.getFile() != null) {
            photoPath = GoogleCloudFileService.generateFileName();
            fileService.upload(postRequest.getFile(), photoPath);
        }
        Optional<Peak> optionalPeak = this.peakStorage.findPeakById(peakId);
        String finalPhotoPath = photoPath;
        return optionalPeak
                .map(peak -> {
                    PeakPost peakPost = postRequest.getFile() != null ? new PeakPost(user, postRequest.getContent(), peak, postRequest.getLatitude(), postRequest.getLongitude(), finalPhotoPath) : new PeakPost(user, postRequest.getContent(), peak, postRequest.getLatitude(), postRequest.getLongitude());
                    peakPostsStorage.createPeakPost(peakPost);
                    PeakPostResponse peakPostResponse = null;
                    try {
                        peakPostResponse = this.peakPostsStorage.findPeakPostById(peakId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new ResponseEntity<>(peakPostResponse, HttpStatus.CREATED);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
