package ShowAndGame.ShowAndGame.Persistence.Entities;

import jakarta.persistence.Entity;

import java.util.Date;
import java.util.List;

@Entity
public class ReviewPost extends Post {

    private Integer rating;

    public ReviewPost(){

    }
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
