package ShowAndGame.ShowAndGame.Persistence.Dto;

import ShowAndGame.ShowAndGame.Persistence.Entities.*;

import java.util.ArrayList;
import java.util.List;

public class GameForCreationAndUpdateDto {
    private String name;
    private String description;
    private int rating;
    private String profileImage;
    private String backgroundImage;
    private List<FeedPost> feedPosts = new ArrayList<>();
    private List<ReviewPost> reviews = new ArrayList<>();
    private List<User> followers = new ArrayList<>();
    private Integer followerAmount = 0;

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

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public Integer getFollowerAmount() {
        return followerAmount;
    }

    public void setFollowerAmount(Integer followerAmount) {
        this.followerAmount = followerAmount;
    }
}
