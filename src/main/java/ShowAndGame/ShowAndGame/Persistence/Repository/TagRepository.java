package ShowAndGame.ShowAndGame.Persistence.Repository;

import ShowAndGame.ShowAndGame.Persistence.Entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByGames_Id(Long gameId);
    @Query("SELECT t FROM Tag t JOIN t.games g WHERE g.id = :gameId")
    List<Tag> findTagsByGameId(@Param("gameId") Long gameId);
}
