package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.db.PostStorage;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.Post;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Api(tags = "Posts")
@RequestMapping("api/public/posts")
public class PostApiController {
    private final PostStorage postStorage;

    @GetMapping("{postid}")
    public ResponseEntity<Post> getPost(@PathVariable("postid") Long postId) {
        Optional<Post> post = this.postStorage.getPostbyId(postId);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/post")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createPost(@RequestParam String content, @AuthenticationPrincipal User user) {
        Post post = new Post(user, content);
        postStorage.createPost(post);
        return post.getId();
    }
}
