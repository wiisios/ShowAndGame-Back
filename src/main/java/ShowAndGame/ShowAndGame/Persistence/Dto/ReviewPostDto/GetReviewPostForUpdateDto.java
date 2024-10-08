package ShowAndGame.ShowAndGame.Persistence.Dto.ReviewPostDto;

import ShowAndGame.ShowAndGame.Persistence.Entities.ReviewPost;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;

import java.time.LocalDate;

public class GetReviewPostForUpdateDto {
    private String description;
    private Integer rating;


    public GetReviewPostForUpdateDto(ReviewPost reviewPost) {
        this.description = reviewPost.getDescription();
        this.rating = reviewPost.getRating();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }


}

