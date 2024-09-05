package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Entities.User;
import ShowAndGame.ShowAndGame.Persistence.Entities.UserDev;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserDevRepository;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDevService {
    private UserDevRepository userDevRepository;
    @Autowired
    public UserDevService(UserDevRepository userDevRepository){
        this.userDevRepository = userDevRepository;
    }

    public UserDev create(UserDev userDev) {
        return userDevRepository.save(userDev);
    }

    public void delete(Long id) {
        userDevRepository.deleteById(id);
    }

    public Optional<UserDev> search(Long id) {
        return userDevRepository.findById(id);
    }

    public List<UserDev> searchAll() {
        return userDevRepository.findAll();
    }

    public UserDev update(UserDev userDev) {
        return userDevRepository.save(userDev);
    }
}
