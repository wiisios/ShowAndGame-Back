package ShowAndGame.ShowAndGame.Persistence.Repository;

import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game , Long> {

    List<Game> findByFollows_UserWhoFollowed_IdAndFollows_IsFollowedTrue(Long userId);
    List<Game> findByOwnerId(Long developerId);
}
