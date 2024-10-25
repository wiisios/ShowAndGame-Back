package ShowAndGame.ShowAndGame.Persistence.Repository;

import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import ShowAndGame.ShowAndGame.Persistence.Entities.ReviewPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewPostRepository extends JpaRepository<ReviewPost, Long> {

    List<ReviewPost> findByGameId(Long gameId);
    @Query("SELECT r.game FROM ReviewPost r WHERE r.id = :reviewPostId")
    Game findGameByReviewPostId(@Param("reviewPostId") Long reviewPostId);
}
