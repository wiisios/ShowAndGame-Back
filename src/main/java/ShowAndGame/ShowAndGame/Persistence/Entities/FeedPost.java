package ShowAndGame.ShowAndGame.Persistence.Entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class FeedPost extends Post {

    private String image;
    private Integer likes;

    @OneToMany(mappedBy = "feedPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;


    public FeedPost(){

    }
    public FeedPost(String description, User user, Game game, LocalDate date, String image, Integer likes1, List<Comment> comments) {
        super(description, user, game, date);
        this.image = image;
        this.likes = likes1;
        this.comments = comments;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String images) {
        this.image = images;
    }


    public Integer getLikes() {
        return likes;
    }


    public void setLikes(Integer likes) {
        this.likes = likes;
    }


    public List<Comment> getComments() {
        return comments;
    }


    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
