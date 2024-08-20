package ShowAndGame.ShowAndGame.Persistence.Entities;

import java.util.Date;
import java.util.List;

public class ReviewPost extends Post {

    private Integer rating;
    public ReviewPost(String content, User user, Game game, Date date, Integer likes, List<Comment> comments, Integer rating) {
        super(content, user, game, date, likes, comments);
        this.rating = rating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
