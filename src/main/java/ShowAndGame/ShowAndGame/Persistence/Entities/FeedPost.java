package ShowAndGame.ShowAndGame.Persistence.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class FeedPost extends Post {

    private String image;
    private Integer likesCounter;
    @OneToMany(mappedBy = "feedPostLiked", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLike> userLikes;

    @OneToMany(mappedBy = "feedPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;


    public FeedPost(){

    }
    public FeedPost(String description, User user, Game game, LocalDate date, String image, List<Comment> comments, Integer likesCounter, List<UserLike> userLikes) {
        super(description, user, game, date);
        this.image = image;
        this.comments = comments;
        this.likesCounter = likesCounter;
        this.userLikes = userLikes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String images) {
        this.image = images;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getLikesCounter() {
        return likesCounter;
    }

    public void setLikesCounter(Integer likesCounter) {
        this.likesCounter = likesCounter;
    }

    public List<UserLike> getLikes() {
        return userLikes;
    }

    public void setLikes(List<UserLike> userLikes) {
        this.userLikes = userLikes;
    }
}
