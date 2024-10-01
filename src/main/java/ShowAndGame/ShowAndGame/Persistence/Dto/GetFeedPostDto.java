package ShowAndGame.ShowAndGame.Persistence.Dto;

import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;

import java.time.LocalDate;

public class GetFeedPostDto {

    private Long id;
    private String description;
    private String image;
    private Integer likes;
    private LocalDate date;
    private String username;
    private String userProfileImage;


    public GetFeedPostDto(FeedPost feedPost) {
        this.id = feedPost.getId();
        this.description = feedPost.getDescription();
        this.image = feedPost.getImage();
        this.likes = feedPost.getLikes();
        this.date = feedPost.getDate();
        this.username = feedPost.getUser().getUsername();
        this.userProfileImage = feedPost.getUser().getProfileImage();
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }
}
