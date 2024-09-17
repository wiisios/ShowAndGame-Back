package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Entities.UserDev;
import ShowAndGame.ShowAndGame.Persistence.Repository.UserDevRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDevService {
    private final UserDevRepository userDevRepository;
    @Autowired
    public UserDevService(UserDevRepository userDevRepository){
        this.userDevRepository = userDevRepository;
    }

    public UserDev Create(UserDev userDev) {
        return userDevRepository.save(userDev);
    }

    public void Delete(Long id) {
        userDevRepository.deleteById(id);
    }

    public Optional<UserDev> GetById(Long id) {
        return userDevRepository.findById(id);
    }

    public Optional<UserDev> GetByUserName (String userName){
        return userDevRepository.findByUserName(userName);
    }

    public List<UserDev> GetAll() {
        return userDevRepository.findAll();
    }

    public UserDev Update(UserDev userDev) {
        return userDevRepository.save(userDev);
    }
}
