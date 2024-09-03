package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;
import ShowAndGame.ShowAndGame.Persistence.Entities.ReviewPost;
import ShowAndGame.ShowAndGame.Persistence.Repository.FeedPostRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.ReviewPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewPostService {
    private ReviewPostRepository reviewPostRepository;

    @Autowired
    public ReviewPostService(ReviewPostRepository reviewPostRepository){
        this.reviewPostRepository = reviewPostRepository;
    }

    public ReviewPost create(ReviewPost reviewPost) {
        return reviewPostRepository.save(reviewPost);
    }

    public void delete(Long id) {
        reviewPostRepository.deleteById(id);
    }

    public Optional<ReviewPost> search(Long id) {
        return reviewPostRepository.findById(id);
    }

    public List<ReviewPost> searchAll() {
        return reviewPostRepository.findAll();
    }

    public List<ReviewPost> searchReviewPostsByGameId(Long gameId){
        return  reviewPostRepository.findByGameId(gameId);
    }

    public ReviewPost update(ReviewPost reviewPost) {
        return reviewPostRepository.save(reviewPost);
    }
}
