package ShowAndGame.ShowAndGame.Persistence.Dto.ReviewPostDto;

import ShowAndGame.ShowAndGame.Persistence.Entities.ReviewPost;

public class ReviewPostForCreationAndUpdateDto {
    private String description;
    private Integer rating;

    public ReviewPostForCreationAndUpdateDto(){
    }
    public ReviewPostForCreationAndUpdateDto(ReviewPost reviewPost) {
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

