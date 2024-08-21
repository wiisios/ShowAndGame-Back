package ShowAndGame.ShowAndGame.Persistence.Entities;

import java.util.Date;
import java.util.List;

public class FeedPost extends Post {

    private List<String> images;
    private Integer likes;
    private List<Comment> comments;

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
