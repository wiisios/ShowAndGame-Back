package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;
import ShowAndGame.ShowAndGame.Persistence.Repository.FeedPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedPostService {
    private final FeedPostRepository feedPostRepository;
    @Autowired
    public FeedPostService(FeedPostRepository feedPostRepository){
        this.feedPostRepository = feedPostRepository;
    }

    public FeedPost Create(FeedPost feedPost) {
        return feedPostRepository.save(feedPost);
    }

    public void Delete(Long id) {
        feedPostRepository.deleteById(id);
    }

    public Optional<FeedPost> GetById(Long id) {
        return feedPostRepository.findById(id);
    }

    public List<FeedPost> GetAll() {
        return feedPostRepository.findAll();
    }

    public List<FeedPost> GetFeedPostsByGameId(Long gameId){
        return  feedPostRepository.findByGameId(gameId);
    }

    public FeedPost Update(FeedPost feedPost) {
        return feedPostRepository.save(feedPost);
    }
}
