package ShowAndGame.ShowAndGame.Persistence.Dto;

import ShowAndGame.ShowAndGame.Persistence.Entities.ReviewPost;
import ShowAndGame.ShowAndGame.Persistence.Entities.User;

import java.time.LocalDate;

public class GetReviewPostDto {
    private Long id;
    private String description;
    private LocalDate date;
    private Integer rating;
    private String userName;
    private String userProfileImage;

    public GetReviewPostDto(ReviewPost reviewPost, User user) {
        this.id = reviewPost.getId();
        this.description = reviewPost.getDescription();
        this.date = reviewPost.getDate();
        this.rating = reviewPost.getRating();
        this.userName = user.getUsername();
        this.userProfileImage = user.getProfileImage();
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }
}
