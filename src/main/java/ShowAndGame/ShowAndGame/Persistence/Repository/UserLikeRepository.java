package ShowAndGame.ShowAndGame.Persistence.Repository;

import ShowAndGame.ShowAndGame.Persistence.Entities.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    @Query("SELECT l FROM UserLike l JOIN l.userWhoLiked u JOIN l.feedPostLiked p WHERE u.id = :userId AND p.id = :feedPostId")
    Optional<UserLike> findLikeByUserIdAndFeedPostId(@Param("userId") Long userId, @Param("feedPostId") Long feedPostId);

    @Query("SELECT ul.feedPostLiked.id FROM UserLike ul WHERE ul.userWhoLiked.id = :userId AND ul.isLiked = true")
    Set<Long> findLikedPostIdsByUserId(@Param("userId") Long userId);
}
