package ShowAndGame.ShowAndGame.Persistence.Entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class FeedPost extends Post {

    private List<String> images = new ArrayList<>();
    private Integer likes;

    @OneToMany(mappedBy = "feedPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments =  new ArrayList<>();


    public FeedPost(){

    }
    public FeedPost(String content, User user, Game game, Date date, List<String> images, Integer likes1, List<Comment> comments) {
        super(content, user, game, date);
        this.images = images;
        this.likes = likes1;
        this.comments = comments;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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
