package pl.edu.agh.ki.io.db;

import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.wallElements.Post;

import java.util.Optional;

@Service
public class PostStorage {
    private final PostRepository postRepository;

    public PostStorage(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public void createPost(Post post) {
        this.postRepository.save(post);
    }

    public Optional<Post> getPostbyId(Long postId) {
        return this.postRepository.findById(postId);
    }
}
