package ShowAndGame.ShowAndGame.Persistence.Dto;

import ShowAndGame.ShowAndGame.Persistence.Entities.ReviewPost;

import java.time.LocalDate;

public class GetReviewPostDto {
    private Long id;
    private String description;
    private LocalDate date;
    private Integer rating;

    public GetReviewPostDto(ReviewPost reviewPost) {
        this.id = reviewPost.getId();
        this.description = reviewPost.getDescription();
        this.date = reviewPost.getDate();
        this.rating = reviewPost.getRating();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
