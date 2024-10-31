package ShowAndGame.ShowAndGame.Persistence.Repository;

import ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.GetFeedPostDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedPostRepository extends JpaRepository<FeedPost, Long> {
    List<FeedPost> findByGameId(Long gameId);

    @Query("SELECT new ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.GetFeedPostDto(fp.id, fp.description, fp.image, fp.likesCounter, fp.date, u.userName, u.profileImage, " +
            "(CASE WHEN ul.id IS NOT NULL THEN true ELSE false END)) " +
            "FROM FeedPost fp " +
            "JOIN fp.user u " +
            "LEFT JOIN UserLike ul ON ul.feedPostLiked.id = fp.id AND ul.userWhoLiked.id = :userId AND ul.isLiked = true")
    List<GetFeedPostDto> findAllWithLikes(@Param("userId") Long userId);

    @Query("SELECT new ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.GetFeedPostDto(fp.id, fp.description, fp.image, fp.likesCounter, fp.date, u.userName, u.profileImage, " +
            "(CASE WHEN ul.id IS NOT NULL THEN true ELSE false END)) " +
            "FROM FeedPost fp " +
            "JOIN fp.user u " +
            "LEFT JOIN UserLike ul ON ul.feedPostLiked.id = fp.id AND ul.userWhoLiked.id = :userId AND ul.isLiked = true " +
            "WHERE fp.id = :feedPostId")
    GetFeedPostDto findByIdWithLikes(@Param("feedPostId") Long feedPostId, @Param("userId") Long userId);

    @Query("SELECT new ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto.GetFeedPostDto(fp.id, fp.description, fp.image, fp.likesCounter, fp.date, u.userName, u.profileImage, " +
            "(CASE WHEN ul.id IS NOT NULL THEN true ELSE false END)) " +
            "FROM FeedPost fp " +
            "JOIN fp.user u " +
            "LEFT JOIN UserLike ul ON ul.feedPostLiked.id = fp.id AND ul.userWhoLiked.id = :userId AND ul.isLiked = true " +
            "WHERE fp.game.id = :gameId")
    List<GetFeedPostDto> findAllByGameIdWithLikes(@Param("gameId") Long gameId, @Param("userId") Long userId);
}
