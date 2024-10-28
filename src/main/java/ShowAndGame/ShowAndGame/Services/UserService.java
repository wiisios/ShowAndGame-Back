package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.UserDto.*;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Persistence.Entities.UserRole;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public GetUserByIdDto GetById(Long id) {
        Optional<User> user = userRepository.findById(id);
        User currentUser = null;

        if (user.isPresent()) {
            currentUser = user.get();
        }

        return new GetUserByIdDto(currentUser);
    }

    public List<GetAllUsersDto> GetAll() {
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .map(GetAllUsersDto::new)
                .collect(Collectors.toList());
    }

    public User Create(UserForCreationDto newUser) {
        User user = new User();

        user.setUserName(newUser.getUserName());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setEmail(newUser.getEmail());
        user.setProfileImage(null);
        user.setBackgroundImage(null);
        user.setUserRole(UserRole.USER);
        user.setComments(new ArrayList<>());
        user.setOwnedGames(new ArrayList<>());
        user.setReviewPosts(new ArrayList<>());
        user.setFeedPosts(new ArrayList<>());
        user.setUserLikes(new ArrayList<>());
        user.setFollows(new ArrayList<>());

        return userRepository.save(user);
    }

    //Method for Users to edit their own profile
    public void UpdateProfile(GetUserForUpdateProfileDto userToUpdate, Long userId) {
        Optional<User> currentUser = userRepository.findById(userId);
        User user = null;

        if(currentUser.isPresent()) {
            user = currentUser.get();
            user.setBackgroundImage(userToUpdate.getBackgroundImage());
            user.setProfileImage(userToUpdate.getProfileImage());
            userRepository.save(user);
        }
    }

    //Method to edit a User as an Admin
    public void UpdateUser(GetUserForAdminUpdateDto userToUpdate, Long userId) {
        Optional<User> currentUser = userRepository.findById(userId);
        User user = null;

        if(currentUser.isPresent()) {
            user = currentUser.get();
            user.setUserName(userToUpdate.getUserName());
            user.setUserRole(userToUpdate.getUserRole());
            userRepository.save(user);
        }
    }

    public void Delete(Long id) {
        userRepository.deleteById(id);
    }
}
