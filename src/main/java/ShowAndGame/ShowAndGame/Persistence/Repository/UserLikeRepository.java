package ShowAndGame.ShowAndGame.Persistence.Repository;

import ShowAndGame.ShowAndGame.Persistence.Entities.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    @Query("SELECT l FROM UserLike l JOIN l.userWhoLiked u JOIN l.feedPostLiked p WHERE u.id = :userId AND p.id = :feedPostId")
    Optional<UserLike> findLikeByUserIdAndFeedPostId(@Param("userId") Long userId, @Param("feedPostId") Long feedPostId);
}
