package ShowAndGame.ShowAndGame.Persistence.Repository;

import ShowAndGame.ShowAndGame.Persistence.Entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l JOIN l.usersWhoLiked u JOIN l.feedPostsLiked p WHERE u.id = :userId AND p.id = :feedPostId")
    Optional<Like> findLikeByUserIdAndFeedPostId(@Param("userId") Long userId, @Param("feedPostId") Long feedPostId);
}
