package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.api.models.CreatePostRequest;
import pl.edu.agh.ki.io.api.models.PostResponse;
import pl.edu.agh.ki.io.db.PostStorage;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.Post;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Api(tags = "Posts")
@RequestMapping("api/public/posts")
public class PostApiController {
    private final PostStorage postStorage;
    Logger logger = LoggerFactory.getLogger(PostApiController.class);

    @GetMapping("/{postid}")
    public ResponseEntity<PostResponse> getPost(@PathVariable("postid") Long postId) {
        PostResponse response = this.postStorage.findPostById(postId);
        if (response == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/post")
    public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostRequest postRequest, @AuthenticationPrincipal User user) {
        Post post = new Post(user, postRequest.getContent());
        postStorage.createPost(post);

        PostResponse response = this.postStorage.findPostById(post.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
