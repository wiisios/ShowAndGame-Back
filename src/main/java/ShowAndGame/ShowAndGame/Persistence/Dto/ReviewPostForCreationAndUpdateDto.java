package ShowAndGame.ShowAndGame.Persistence.Dto;

public class ReviewPostForCreationAndUpdateDto {
    private String description;
    private Integer rating;

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
