package ShowAndGame.ShowAndGame.Persistence.Entities;

import jakarta.persistence.*;

@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userWhoFollowed;
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game gameFollowed;
    private boolean isFollowed;

    public Follow(){
    }

    public Follow(Long id, User userWhoFollowed, Game gameFollowed, boolean isFollowed) {
        this.id = id;
        this.userWhoFollowed = userWhoFollowed;
        this.gameFollowed = gameFollowed;
        this.isFollowed = isFollowed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserWhoFollowed() {
        return userWhoFollowed;
    }

    public void setUserWhoFollowed(User userWhoFollowed) {
        this.userWhoFollowed = userWhoFollowed;
    }

    public Game getGameFollowed() {
        return gameFollowed;
    }

    public void setGameFollowed(Game gameFollowed) {
        this.gameFollowed = gameFollowed;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }
}
