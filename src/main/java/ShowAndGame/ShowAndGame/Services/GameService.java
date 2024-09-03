package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.GameForFeedDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Game;
import ShowAndGame.ShowAndGame.Persistence.Repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    private GameRepository gameRepository;
    @Autowired
    public GameService(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    public Game create(Game game) {
        return gameRepository.save(game);
    }

    public void delete(Long id) {
        gameRepository.deleteById(id);
    }

    public Optional<Game> search(Long id) {
        return gameRepository.findById(id);
    }

    public List<Game> searchAll() {
        return gameRepository.findAll();
    }

    public List<GameForFeedDto> searchAllForFeed() {
        List<Game> games = gameRepository.findAll();
        return games.stream()
                .map(game -> new GameForFeedDto(game))
                .collect(Collectors.toList());
    }

    public Game update(Game game) {
        return gameRepository.save(game);
    }
}
