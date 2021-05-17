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
import pl.edu.agh.ki.io.api.models.CreatePostResponse;
import pl.edu.agh.ki.io.db.PostStorage;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.Post;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Api(tags = "Posts")
@RequestMapping("api/public/posts")
public class PostApiController {
    private final PostStorage postStorage;
    Logger logger = LoggerFactory.getLogger(PostApiController.class);

    @GetMapping("/{postid}")
    public ResponseEntity<Post> getPost(@PathVariable("postid") Long postId) {
        Optional<Post> post = this.postStorage.getPostbyId(postId);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePostResponse createPost(@RequestBody CreatePostRequest postRequest, @AuthenticationPrincipal User user) {
        Post post = new Post(user, postRequest.getContent());
        postStorage.createPost(post);

        return CreatePostResponse.fromPost(post);
    }
}
