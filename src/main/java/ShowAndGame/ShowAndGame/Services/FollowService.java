package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.FollowDto.FollowForCreationDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.FollowDto.GetFollowDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.*;
import ShowAndGame.ShowAndGame.Persistence.Repository.*;
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
    public FollowService(FollowRepository followRepository, UserRepository userRepository, GameRepository gameRepository){
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public void Create (FollowForCreationDto followToCreate){
        Follow newFollow = new Follow();
        User userWhoFollowed = userRepository.findById(followToCreate.getUserId()).get();
        Game followedGame = gameRepository.findById(followToCreate.getGameId()).get();

        newFollow.setUserWhoFollowed(userWhoFollowed);
        newFollow.setGameFollowed(followedGame);
        newFollow.setFollowed(true);

        followRepository.save(newFollow);
    }

    public Follow GetFollowById(Long id){
        Optional<Follow> follow = followRepository.findById(id);
        return follow.orElse(null);
    }

    public List<Follow> GetAll(){
        return followRepository.findAll();
    }

    public void Update(GetFollowDto follow){
        Optional<Follow> currentFollow = followRepository.findById(follow.getId());

        if (currentFollow.isPresent()){
            Follow followToUpdate = currentFollow.get();
            followToUpdate.setFollowed(follow.isFollowed());
            followRepository.save(followToUpdate);
        }
    }

    public void Delete(Long id){
        Optional<Follow> currentFollow = followRepository.findById(id);

        if(currentFollow.isPresent()){
            Follow follow = currentFollow.get();
            followRepository.delete(follow);
        }

    }

    public boolean isFollowedCheck(Long userId, Long gameId) {
        Optional<Follow> follow = followRepository.findFollowByUserIdAndGameId(userId, gameId);
        return follow.map(Follow::isFollowed).orElse(false);
    }

    public void toggleFollow(Long userId, Long gameId) {
        Optional<Follow> follow = followRepository.findFollowByUserIdAndGameId(userId, gameId);

        if (follow.isPresent()) {
            Follow existingFollow = follow.get();
            existingFollow.setFollowed(!existingFollow.isFollowed());
            followRepository.save(existingFollow);
        } else {
            FollowForCreationDto newFollow = new FollowForCreationDto(userId, gameId);
            Create(newFollow);
        }
    }
}
