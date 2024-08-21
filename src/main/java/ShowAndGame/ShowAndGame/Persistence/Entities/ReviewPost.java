package ShowAndGame.ShowAndGame.Persistence.Entities;

import java.util.Date;
import java.util.List;

public class ReviewPost extends Post {

    private Integer rating;
    public ReviewPost(String content, User user, Game game, Date date, Integer rating) {
        super(content, user, game, date);
        this.rating = rating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
