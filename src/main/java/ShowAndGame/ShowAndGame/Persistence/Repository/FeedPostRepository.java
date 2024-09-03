package ShowAndGame.ShowAndGame.Persistence.Repository;

import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;
import ShowAndGame.ShowAndGame.Persistence.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedPostRepository extends JpaRepository<FeedPost, Long> {
    List<FeedPost> findByGameId(Long gameId);
}
