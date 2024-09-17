package ShowAndGame.ShowAndGame.Persistence.Dto;

import ShowAndGame.ShowAndGame.Persistence.Entities.*;

import java.util.List;

public class GetGameDto {
    private Long id;
    private String name;
    private String description;
    private int rating;
    private String profileImage;
    private String backgroundImage;
    private List<FeedPost> feedPosts;
    private List<ReviewPost> reviews;
    private Integer followerAmount;
    private List<Tag> tags;

    public GetGameDto(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.description = game.getDescription();
        this.rating = game.getRating();
        this.profileImage = game.getProfileImage();
        this.backgroundImage = game.getBackgroundImage();
        this.feedPosts = game.getPosts();
        this.reviews = game.getReviews();
        this.followerAmount = game.getFollowerAmount();
        this.tags = game.getTags();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public List<FeedPost> getFeedPosts() {
        return feedPosts;
    }

    public void setFeedPosts(List<FeedPost> feedPosts) {
        this.feedPosts = feedPosts;
    }

    public List<ReviewPost> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewPost> reviews) {
        this.reviews = reviews;
    }

    public Integer getFollowerAmount() {
        return followerAmount;
    }

    public void setFollowerAmount(Integer followerAmount) {
        this.followerAmount = followerAmount;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
