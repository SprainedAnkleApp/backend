package pl.edu.agh.ki.io.db;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.api.models.CommentResponse;
import pl.edu.agh.ki.io.models.Comment;
import pl.edu.agh.ki.io.models.CommentPage;
import pl.edu.agh.ki.io.models.wallElements.WallItem;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentStorage {
    private final CommentRepository commentRepository;
    private final WallItemRepository wallItemRepository;

    public Page<CommentResponse> findAllByWallItem(CommentPage commentPage, Long wallItemId) {
        Optional<WallItem> wallItem = this.wallItemRepository.findById(wallItemId);
        if(wallItem.isEmpty()) return null;

        Sort sort = Sort.by(commentPage.getSortDirection(), commentPage.getSortBy());
        Pageable pageable = PageRequest.of(commentPage.getPageNumber(),
                commentPage.getPageSize(), sort);
        Page<Comment> comments = this.commentRepository.findAllByWallItem(pageable, wallItem.get());

        return new PageImpl<>(comments.stream()
                .map(comment -> {
                    try {
                        return CommentResponse.fromComment(comment);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList()),
                pageable, comments.getTotalElements());
    }

    public Comment createComment(Comment comment) {
        return this.commentRepository.save(comment);
    }

    public CommentResponse findCommentById(Long id) throws IOException {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isEmpty()) return null;
        return CommentResponse.fromComment(comment.get());
    }
}
