package ShowAndGame.ShowAndGame.Persistence.Entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Entity
public class UserDev extends User {

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Game> ownedGames;

    public UserDev(){

    }
    public UserDev(String userName, String password, String email, String profileImage, String backgroundImage, UserRole userRole, List<ReviewPost> reviewPosts, List<FeedPost> feedPosts, List<Comment> comments, List<Game> followedGames, List<Game> ownedGames) {
        super(userName, password, email, profileImage, backgroundImage, userRole, reviewPosts, feedPosts,comments, followedGames);
        this.ownedGames = ownedGames;
    }

    public List<Game> getOwnedGames() {
        return ownedGames;
    }

    public void setOwnedGames(List<Game> ownedGames) {
        this.ownedGames = ownedGames;
    }
}
