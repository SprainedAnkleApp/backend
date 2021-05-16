package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.db.PeakPostsStorage;
import pl.edu.agh.ki.io.db.PeakStorage;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.PeakPost;
import pl.edu.agh.ki.io.models.wallElements.PeakPostPage;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Api(tags = "PeakPosts")
@RequestMapping("api/public/peak/")
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
    @ResponseStatus(HttpStatus.CREATED)
    public Long createPost(@RequestParam String content, @AuthenticationPrincipal User user, @PathVariable("peakid") Long peakId) {
        Optional<Peak> peak = this.peakStorage.findPeakById(peakId);
        PeakPost peakPost = new PeakPost(user, content, peak.get());
        peakPostsStorage.createPeakPost(peakPost);
        return peakPost.getId();
    }
}
