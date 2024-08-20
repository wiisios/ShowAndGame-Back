package ShowAndGame.ShowAndGame.Persistence.Entities;

import java.util.Date;
import java.util.List;

public class FeedPost extends Post {

    private List<String> images;
    private Integer likes;
    private List<Comment> comments;
    private Integer commentAmount;

    public FeedPost(String content, User user, Game game, Date date, Integer likes, List<Comment> comments, List<String> images, Integer likes1, List<Comment> comments1, Integer commentAmount) {
        super(content, user, game, date, likes, comments);
        this.images = images;
        this.likes = likes1;
        this.comments = comments1;
        this.commentAmount = commentAmount;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public Integer getLikes() {
        return likes;
    }

    @Override
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getCommentAmount() {
        return commentAmount;
    }

    public void setCommentAmount(Integer commentAmount) {
        this.commentAmount = commentAmount;
    }
}
