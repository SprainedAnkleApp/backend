package pl.edu.agh.ki.io.db;

import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.api.models.PostResponse;
import pl.edu.agh.ki.io.models.wallElements.Post;

import java.util.Optional;

@Service
public class PostStorage {
    private final PostRepository postRepository;
    private final ReactionsRepository reactionsRepository;
    public PostStorage(PostRepository postRepository, ReactionsRepository reactionsRepository){
        this.postRepository = postRepository;
        this.reactionsRepository = reactionsRepository;
    }

    public void createPost(Post post) {
        this.postRepository.save(post);
    }

    public PostResponse getPostbyId(Long postId) {
        Optional<Post> post = this.postRepository.findById(postId);
        if(post.isEmpty()) return null;
        return PostResponse.fromPostAndReactions(post.get(), reactionsRepository.findByIdWallElementID(postId));
    }
}
