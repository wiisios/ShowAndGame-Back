package ShowAndGame.ShowAndGame.Persistence.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class ReviewPost extends Post {
    @Column
    private int rating;

    public ReviewPost(){
    }

    public ReviewPost(String description, User user, Game game, LocalDate date, Integer rating) {
        super(description, user, game, date);
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
