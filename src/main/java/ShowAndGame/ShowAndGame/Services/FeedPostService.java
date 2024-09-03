package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;
import ShowAndGame.ShowAndGame.Persistence.Entities.Post;
import ShowAndGame.ShowAndGame.Persistence.Repository.FeedPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedPostService {
    private FeedPostRepository feedPostRepository;
    @Autowired
    public FeedPostService(FeedPostRepository feedPostRepository){
        this.feedPostRepository = feedPostRepository;
    }

    public FeedPost create(FeedPost feedPost) {
        return feedPostRepository.save(feedPost);
    }

    public void delete(Long id) {
        feedPostRepository.deleteById(id);
    }

    public Optional<FeedPost> search(Long id) {
        return feedPostRepository.findById(id);
    }

    public List<FeedPost> searchAll() {
        return feedPostRepository.findAll();
    }

    public List<FeedPost> searchFeedPostsByGameId(Long gameId){
        return  feedPostRepository.findByGameId(gameId);
    }

    public FeedPost update(FeedPost feedPost) {
        return feedPostRepository.save(feedPost);
    }
}
