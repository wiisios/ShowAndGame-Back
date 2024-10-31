package ShowAndGame.ShowAndGame.Persistence.Dto.FeedPostDto;

import ShowAndGame.ShowAndGame.Persistence.Entities.FeedPost;

import java.time.LocalDate;

public class GetFeedPostDto {
    private Long id;
    private String description;
    private String image;
    private Integer likesCounter;
    private LocalDate date;
    private String username;
    private String userProfileImage;
    private boolean isLiked;

    public GetFeedPostDto(FeedPost feedPost, boolean isLiked) {
        this.id = feedPost.getId();
        this.description = feedPost.getDescription();
        this.image = feedPost.getImage();
        this.likesCounter = feedPost.getLikesCounter();
        this.date = feedPost.getDate();
        this.username = feedPost.getUser().getUsername();
        this.userProfileImage = feedPost.getUser().getProfileImage();
        this.isLiked = isLiked;
    }

    public GetFeedPostDto(Long id, String description, String image, Integer likesCounter, LocalDate date,
                          String username, String userProfileImage, boolean isLiked) {
        this.id = id;
        this.description = description;
        this.image = image;
        this.likesCounter = likesCounter;
        this.date = date;
        this.username = username;
        this.userProfileImage = userProfileImage;
        this.isLiked = isLiked;
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

    public Integer getLikesCounter() {
        return likesCounter;
    }

    public void setLikesCounter(Integer likesCounter) {
        this.likesCounter = likesCounter;
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

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
