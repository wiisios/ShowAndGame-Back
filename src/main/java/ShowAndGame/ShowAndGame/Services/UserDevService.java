package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.GetAllUsersDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GetUserByIdDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.GetUserForUpdateProfileDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.UserForCreationDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.UserDev;
import ShowAndGame.ShowAndGame.Persistence.Entities.UserRole;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserDevRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDevService {
    private final UserDevRepository userDevRepository;
    @Autowired
    public UserDevService(UserDevRepository userDevRepository){
        this.userDevRepository = userDevRepository;
    }

    public UserDev Create(UserForCreationDto newUserDev) {
        UserDev userDev = new UserDev();

        userDev.setUserName(newUserDev.getUserName());
        userDev.setPassword(newUserDev.getPassword());
        userDev.setEmail(newUserDev.getEmail());
        userDev.setProfileImage(null);
        userDev.setBackgroundImage(null);
        userDev.setUserRole(UserRole.DEVELOPER);
        userDev.setFollowedGames(new ArrayList<>());
        userDev.setFeedPosts(new ArrayList<>());
        userDev.setReviewPosts(new ArrayList<>());
        userDev.setComments(new ArrayList<>());
        userDev.setOwnedGames(new ArrayList<>());

        return userDevRepository.save(userDev);
    }

    public void Delete(Long id) {
        userDevRepository.deleteById(id);
    }

    public GetUserByIdDto GetById(Long id) {
        Optional<UserDev> userDev = userDevRepository.findById(id);
        UserDev currentUserDev = null;

        if (userDev.isPresent()){
            currentUserDev = userDev.get();
        }

        return new GetUserByIdDto(currentUserDev);
    }

    public Optional<UserDev> GetByUserName (String userName){
        return userDevRepository.findByUserName(userName);
    }

    public List<GetAllUsersDto> GetAll() {
        List<UserDev> allUsers = userDevRepository.findAll();
        return allUsers.stream()
                .map(GetAllUsersDto::new)
                .collect(Collectors.toList());
    }

    public void UpdateProfile(GetUserForUpdateProfileDto userDevToUpdate, Long userId) {
        Optional<UserDev> currentUserDev = userDevRepository.findById(userId);
        UserDev userDev = null;

        if(currentUserDev.isPresent()){
            userDev = currentUserDev.get();
            userDev.setBackgroundImage(userDevToUpdate.getBackgroundImage());
            userDev.setProfileImage(userDevToUpdate.getProfileImage());
            userDevRepository.save(userDev);
        }
    }
}
