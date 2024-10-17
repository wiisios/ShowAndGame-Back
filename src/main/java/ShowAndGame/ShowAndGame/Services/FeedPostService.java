package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.FeedPostForCreationdDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.GetFeedPostDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.GetFeedPostForUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Comment;
import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;
import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Persistence.Repository.FeedPostRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.GameRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedPostService {
    private final FeedPostRepository feedPostRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final UserLikeService userLikeService;
    @Autowired
    public FeedPostService(FeedPostRepository feedPostRepository, GameRepository gameRepository, UserRepository userRepository, UserLikeService userLikeService){
        this.feedPostRepository = feedPostRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.userLikeService = userLikeService;
    }

    public void Create(FeedPostForCreationdDto newFeedPost, Long userId, Long gameId) {
        FeedPost feedPostToCreate = new FeedPost();
        Optional<Game> game = gameRepository.findById(gameId);
        Optional<User> user = userRepository.findById(userId);
        LocalDate dateNow = LocalDate.now();

        if (!game.isPresent()) {
            throw new EntityNotFoundException("Game with id " + gameId + " not found");
        }
        if (!user.isPresent()) {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }

        Game currentGame = game.get();
        User currentUser = user.get();

        feedPostToCreate.setDescription(newFeedPost.getDescription());
        feedPostToCreate.setImage(newFeedPost.getImage());
        feedPostToCreate.setUser(currentUser);
        feedPostToCreate.setGame(currentGame);
        feedPostToCreate.setDate(dateNow);
        feedPostToCreate.setComments(new ArrayList<Comment>());
        feedPostToCreate.setLikesCounter(0);

        feedPostRepository.save(feedPostToCreate);
    }

    public void Delete(Long id, Long userId) {
        Optional<FeedPost> currentFeedPost = feedPostRepository.findById(id);
        FeedPost feedPost = null;

        if(currentFeedPost.isPresent()){
            feedPost = currentFeedPost.get();
            if (Objects.equals(feedPost.getUser().getId(), userId)){
                feedPostRepository.deleteById(id);
            }
        }

    }

    public Optional<GetFeedPostDto> GetById(Long id, Long userId) {
        return feedPostRepository.findById(id)
                .map(feedPost -> {
                    boolean isLiked = userLikeService.isLikedCheck(userId, feedPost.getId());
                    return new GetFeedPostDto(feedPost, isLiked); // Pasamos el estado de like
                });
    }

    public List<GetFeedPostDto> GetAll(Long userId) {
        return feedPostRepository.findAll().stream()
                .map(feedPost -> {
                    boolean isLiked = userLikeService.isLikedCheck(userId, feedPost.getId());
                    return new GetFeedPostDto(feedPost, isLiked); // Incluimos el estado del like
                })
                .collect(Collectors.toList());
    }

    public List<GetFeedPostDto> GetFeedPostsByGameId(Long gameId, Long userId) {
        return feedPostRepository.findByGameId(gameId).stream()
                .map(feedPost -> {
                    boolean isLiked = userLikeService.isLikedCheck(userId, feedPost.getId());
                    return new GetFeedPostDto(feedPost, isLiked);
                }).collect(Collectors.toList());
    }

    public void Update(GetFeedPostForUpdateDto feedPostDto, Long userId, Long feedPostId) {
        Optional<FeedPost> currentFeedPost = feedPostRepository.findById(feedPostId);
        FeedPost feedPost = null;

        if(currentFeedPost.isPresent()) {
            feedPost = currentFeedPost.get();
            if (Objects.equals(feedPost.getUser().getId(), userId)) {
                feedPost.setDescription(feedPostDto.getDescription());
                feedPost.setImage(feedPostDto.getImage());
                feedPostRepository.save(feedPost);

            }
        }
    }
}
