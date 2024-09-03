package ShowAndGame.ShowAndGame.Persistence.Repository;

import ShowAndGame.ShowAndGame.Persistence.Entities.ReviewPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewPostRepository extends JpaRepository<ReviewPost, Long> {

    List<ReviewPost> findByGameId(Long gameId);

}
