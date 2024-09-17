package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.CommentDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Comment;
import ShowAndGame.ShowAndGame.Persistence.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {


    private final CommentRepository commentRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public Comment Create(Comment comment) {
        return commentRepository.save(comment);
    }

    public void Delete(Long id) {
        commentRepository.deleteById(id);
    }

    public Optional<Comment> GetById(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> GetAll() {
        return commentRepository.findAll();
    }

    public List<CommentDto> GetCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByFeedPostId(postId);
        return comments.stream()
                .map(comment -> new CommentDto(comment))
                .collect(Collectors.toList());
    }

    public Comment Update(Comment comment) {
        return commentRepository.save(comment);
    }


}
