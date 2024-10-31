package ShowAndGame.ShowAndGame.Persistence.Repository;

import ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.GetGameDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game , Long> {
    List<Game> findByFollows_UserWhoFollowed_IdAndFollows_IsFollowedTrue(Long userId);
    List<Game> findByOwnerId(Long developerId);
    @Query("SELECT g FROM Game g LEFT JOIN FETCH g.tags")
    List<Game> findAllForExplore();
    @Query("""
    SELECT new ShowAndGame.ShowAndGame.Persistence.Dto.GameDto.GetGameDto(
           g.id,
           g.profileImage,
           g.backgroundImage,
           g.name,
           g.description,
           g.rating,
           CASE WHEN f.id IS NOT NULL THEN true ELSE false END,
           o.userName
    )
    FROM Game g
    LEFT JOIN g.owner o
    LEFT JOIN Follow f ON f.userWhoFollowed.id = :userId AND f.gameFollowed.id = g.id
    WHERE g.id = :gameId
    """)
    Optional<GetGameDto> findGameWithOwnerAndFollowStatus(@Param("gameId") Long gameId, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE Game g SET g.totalRating = g.totalRating + :newRating, g.reviewAmount = g.reviewAmount + 1, g.rating = (g.totalRating + :newRating) / (g.reviewAmount + 1) WHERE g.id = :gameId")
    void updateRating(@Param("gameId") Long gameId, @Param("newRating") float newRating);

}
