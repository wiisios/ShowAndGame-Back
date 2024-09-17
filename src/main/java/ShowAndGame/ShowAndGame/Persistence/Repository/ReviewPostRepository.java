package ShowAndGame.ShowAndGame.Persistence.Repository;

import ShowAndGame.ShowAndGame.Persistence.Entities.ReviewPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewPostRepository extends JpaRepository<ReviewPost, Long> {

    List<ReviewPost> findByGameId(Long gameId);
}
