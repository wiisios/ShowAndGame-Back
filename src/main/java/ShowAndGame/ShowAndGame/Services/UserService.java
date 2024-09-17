package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User Create(User user) {
        return userRepository.save(user);
    }

    public void Delete(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> GetById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> GetAll() {
        return userRepository.findAll();
    }

    public User Update(User user) {
        return userRepository.save(user);
    }

    public Optional<User> GetByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}
