package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.api.models.CreatePostRequest;
import pl.edu.agh.ki.io.api.models.CreatePostResponse;
import pl.edu.agh.ki.io.db.PeakPostsStorage;
import pl.edu.agh.ki.io.db.PeakStorage;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.PeakPost;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Api(tags = "PeakPosts")
@RequestMapping("api/public/peak")
public class PeakPostsApiController {

    private final PeakPostsStorage peakPostsStorage;
    private final PeakStorage peakStorage;

    @GetMapping("/{peakid}/posts")
    public List<PeakPost> getPeakPostsByPeakId(@PathVariable("peakid") Long peakId) {
        return this.peakPostsStorage.findPeakPostsByPeakId(peakId);
    }

    @PostMapping("/{peakid}/posts")
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest postRequest, @AuthenticationPrincipal User user, @PathVariable("peakid") Long peakId) {
        Optional<Peak> optionalPeak = this.peakStorage.findPeakById(peakId);
        return optionalPeak
                .map(peak -> {
                    PeakPost peakPost = new PeakPost(user, postRequest.getContent(), peak);
                    peakPostsStorage.createPeakPost(peakPost);
                    return new ResponseEntity<>(CreatePostResponse.fromPost(peakPost), HttpStatus.CREATED);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
