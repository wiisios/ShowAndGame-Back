package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.CommentDto.CommentForCreationAndUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.CommentDto.GetCommentForPostDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.CommentDto.GetCommentForUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.*;
import ShowAndGame.ShowAndGame.Persistence.Repository.CommentRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.FeedPostRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final FeedPostRepository feedPostRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, FeedPostRepository feedPostRepository){

        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.feedPostRepository = feedPostRepository;
    }

    public void Create(CommentForCreationAndUpdateDto commentDto, Long postId, Long userId) {
        Comment commentToCreate = new Comment();
        Optional<FeedPost> feedPost = feedPostRepository.findById(postId);
        Optional<User> user = userRepository.findById(userId);
        FeedPost currentPost = null;
        User currentUser = null;

        if (feedPost.isPresent()) {
            currentPost = feedPost.get();
        }
        if (user.isPresent()){
            currentUser = user.get();
        }

        commentToCreate.setDescription(commentDto.getDescription());
        commentToCreate.setUser(currentUser);
        commentToCreate.setFeedPost(currentPost);

        commentRepository.save((commentToCreate));
    }

    public void Delete(Long commentId, Long userId) {
        Optional<Comment> currentComment = commentRepository.findById(commentId);
        Comment comment = null;

        if(currentComment.isPresent()){
            comment = currentComment.get();
            if (Objects.equals(comment.getUser().getId(), userId)) {
                commentRepository.deleteById(commentId);
            }}

    }

    public Optional<GetCommentForPostDto> GetById(Long id) {
         return commentRepository.findById(id).map(comment -> new GetCommentForPostDto(comment, comment.getUser()))
         ;
    }

    public List<GetCommentForPostDto> GetAll() {
        return commentRepository.findAll().stream()
                .map(comment -> new GetCommentForPostDto(comment, comment.getUser()))
                .collect(Collectors.toList());
    }

    public List<GetCommentForPostDto> GetCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByFeedPostId(postId);
        return comments.stream()
                .map(comment -> new GetCommentForPostDto(comment, comment.getUser()))
                .collect(Collectors.toList());
    }

    public void Update(GetCommentForUpdateDto commentToUpdate, Long userId, Long commentId) {

        Optional<Comment> currentComment = commentRepository.findById(commentId);
        Comment comment = null;

        if(currentComment.isPresent()){
            comment = currentComment.get();
            if (Objects.equals(comment.getUser().getId(), userId)) {
                comment.setDescription(commentToUpdate.getDescription());
                commentRepository.save(comment);
            }
        }
    }


}
