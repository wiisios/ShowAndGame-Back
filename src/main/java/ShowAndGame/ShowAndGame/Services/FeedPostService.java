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
    @Autowired
    public FeedPostService(FeedPostRepository feedPostRepository, GameRepository gameRepository, UserRepository userRepository){
        this.feedPostRepository = feedPostRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public void Create(FeedPostForCreationdDto newFeedPost, Long userId, Long gameId) {
        FeedPost feedPostToCreate = new FeedPost();
        Optional<Game> game = gameRepository.findById(gameId);
        Optional<User> user = userRepository.findById(userId);
        Game currentGame = null;
        User currentUser = null;
        LocalDate dateNow = LocalDate.now();

        if (game.isPresent()) {
            currentGame = game.get();
        }
        if (user.isPresent()){
            currentUser = user.get();
        }

        feedPostToCreate.setDescription(newFeedPost.getDescription());
        feedPostToCreate.setImage(newFeedPost.getImage());
        feedPostToCreate.setUser(currentUser);
        feedPostToCreate.setGame(currentGame);
        feedPostToCreate.setDate(dateNow);
        feedPostToCreate.setComments(new ArrayList<Comment>());
        feedPostToCreate.setLikes(0);

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

    public Optional<GetFeedPostDto> GetById(Long id) {

        return feedPostRepository.findById(id).map(feedPost -> new GetFeedPostDto(feedPost));
    }

    public List<GetFeedPostDto> GetAll() {
        return feedPostRepository.findAll().stream()
                .map(feedPost -> new GetFeedPostDto(feedPost))
                .collect(Collectors.toList());
    }

    public List<GetFeedPostDto> GetFeedPostsByGameId(Long gameId){
        return  feedPostRepository.findByGameId(gameId).stream()
                .map(feedPost -> new GetFeedPostDto(feedPost)).collect(Collectors.toList());
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
