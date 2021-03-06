package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.api.models.CommentResponse;
import pl.edu.agh.ki.io.api.models.CreateCommentRequest;
import pl.edu.agh.ki.io.db.CommentStorage;
import pl.edu.agh.ki.io.db.WallItemStorage;
import pl.edu.agh.ki.io.models.Comment;
import pl.edu.agh.ki.io.models.CommentPage;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.WallItem;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Api(tags = "Comments")
@RequestMapping("api/public/posts")
@RequiredArgsConstructor
public class CommentsApiController{
    private final CommentStorage commentStorage;
    private final WallItemStorage wallItemStorage;

    @GetMapping("{wallitemid}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable("wallitemid") Long wallItemId){
        return new ResponseEntity<>(this.commentStorage.findAllByWallItem(wallItemId), HttpStatus.OK);
    }

    @PostMapping("{wallitemid}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable("wallitemid") Long wallItemId, @RequestBody CreateCommentRequest request, @AuthenticationPrincipal User user) throws IOException {
        Optional<WallItem> wallItem = this.wallItemStorage.getWallItemById(wallItemId);
        if(wallItem.isEmpty()) return ResponseEntity.notFound().build();
        Comment comment = this.commentStorage.createComment(new Comment(user, wallItem.get(), request.getContent()));

        return new ResponseEntity<>(CommentResponse.fromComment(comment), HttpStatus.CREATED);
    }
}
