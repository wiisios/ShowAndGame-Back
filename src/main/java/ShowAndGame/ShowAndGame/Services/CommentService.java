package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Entities.Comment;
import ShowAndGame.ShowAndGame.Persistence.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {


    private CommentRepository commentRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    public Optional<Comment> search(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> searchAll() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByFeedPostId(postId);
    }

    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }


}
