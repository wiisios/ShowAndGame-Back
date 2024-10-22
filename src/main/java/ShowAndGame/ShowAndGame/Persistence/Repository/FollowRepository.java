package ShowAndGame.ShowAndGame.Persistence.Repository;

import ShowAndGame.ShowAndGame.Persistence.Entities.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("SELECT f FROM Follow f JOIN f.userWhoFollowed u JOIN f.gameFollowed g WHERE u.id = :userId AND g.id = :gameId")
    Optional<Follow> findFollowByUserIdAndGameId(@Param("userId") Long userId, @Param("gameId") Long gameId);
}
