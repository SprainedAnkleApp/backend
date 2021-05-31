package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.api.models.CreatePostRequest;
import pl.edu.agh.ki.io.api.models.PeakPostResponse;
import pl.edu.agh.ki.io.api.models.PostResponse;
import pl.edu.agh.ki.io.db.PeakPostsStorage;
import pl.edu.agh.ki.io.db.PeakStorage;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.PeakPost;
import pl.edu.agh.ki.io.models.wallElements.PeakPostPage;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Api(tags = "PeakPosts")
@RequestMapping("api/public/peak")
public class PeakPostsApiController {

    private final PeakPostsStorage peakPostsStorage;
    private final PeakStorage peakStorage;

    @GetMapping("{peakid}/posts")
    public ResponseEntity<Page<PeakPost>> getPeakPostsByPeakId(@PathVariable("peakid") Long peakId, PeakPostPage peakPostPage) {
        if (this.peakStorage.findPeakById(peakId).isPresent())
            return new ResponseEntity<>(this.peakPostsStorage.findPeakPostsByPeakId(peakId, peakPostPage), HttpStatus.OK);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/{peakid}/posts")
    public ResponseEntity<PeakPostResponse> createPost(@RequestBody CreatePostRequest postRequest, @AuthenticationPrincipal User user, @PathVariable("peakid") Long peakId) {
        Optional<Peak> optionalPeak = this.peakStorage.findPeakById(peakId);
        return optionalPeak
                .map(peak -> {
                    PeakPost peakPost = new PeakPost(user, postRequest.getContent(), peak);
                    peakPostsStorage.createPeakPost(peakPost);
                    PeakPostResponse peakPostResponse= this.peakPostsStorage.findPeakPostById(peakId);
                    return new ResponseEntity<>(peakPostResponse, HttpStatus.CREATED);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
