package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.FollowDto.FollowForCreationDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.FollowDto.GetFollowDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.*;
import ShowAndGame.ShowAndGame.Persistence.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Autowired
    public FollowService(FollowRepository followRepository, UserRepository userRepository, GameRepository gameRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public Follow GetById(Long id) {
        Optional<Follow> follow = followRepository.findById(id);

        return follow.orElse(null);
    }

    public List<Follow> GetAll() {
        return followRepository.findAll();
    }

    public void Create (FollowForCreationDto followToCreate) {
        Follow newFollow = new Follow();
        User userWhoFollowed = userRepository.findById(followToCreate.getUserId()).get();
        Game followedGame = gameRepository.findById(followToCreate.getGameId()).get();

        newFollow.setUserWhoFollowed(userWhoFollowed);
        newFollow.setGameFollowed(followedGame);
        newFollow.setFollowed(true);

        followRepository.save(newFollow);
    }

    public void Update(GetFollowDto follow) {
        Optional<Follow> currentFollow = followRepository.findById(follow.getId());

        if (currentFollow.isPresent()){
            Follow followToUpdate = currentFollow.get();
            followToUpdate.setFollowed(follow.isFollowed());
            followRepository.save(followToUpdate);
        }
    }

    @Transactional
    public String toggleFollow(Long userId, Long gameId) {
        Optional<Follow> followOpt = followRepository.findFollowByUserIdAndGameId(userId, gameId);

        if (followOpt.isPresent()) {
            Follow existingFollow = followOpt.get();
            existingFollow.setFollowed(!existingFollow.isFollowed());
            followRepository.save(existingFollow);
            return existingFollow.isFollowed() ? "Follow added" : "Follow removed";
        } else {
            FollowForCreationDto newFollow = new FollowForCreationDto(userId, gameId);
            Create(newFollow);
            return "Follow added";
        }
    }

    public void Delete(Long id) {
        Optional<Follow> currentFollow = followRepository.findById(id);

        if(currentFollow.isPresent()){
            Follow follow = currentFollow.get();
            followRepository.delete(follow);
        }
    }
}
